package models

import java.util.Date

import anorm.SqlParser._
import anorm._
import org.joda.time.DateTime
import play.api.Play.current
import play.api.db._

import scala.language.postfixOps

case class Company(id: Option[Long] = None, name: String)
case class Speaker(id: Option[Long] = None, name: String, session: String, registrated: Option[Date] , companyId: Option[Long])


/**
 * Helper for pagination.
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

object Speaker {
  
  // -- Parsers
  
  /**
   * Parse a Speaker from a ResultSet
   */
  val simple = {
    get[Option[Long]]("speaker.id") ~
    get[String]("speaker.name") ~
    get[String]("speaker.session") ~
    get[Option[Date]]("speaker.registrated") ~
    get[Option[Long]]("speaker.company_id") map {
      case id~name~session~registrated~companyId => Speaker(id, name, session, registrated, companyId)
    }
  }
  
  /**
   * Parse a (Speaker,Company) from a ResultSet
   */
  val withCompany = Speaker.simple ~ (Company.simple ?) map {
    case speaker~company => (speaker,company)
  }
  
  // -- Queries
  
  /**
   * Retrieve a speaker from the id.
   */
  def findById(id: Long): Option[Speaker] = {
    DB.withConnection { implicit connection =>
      SQL("select * from speaker where id = {id}").on('id -> id).as(Speaker.simple.singleOpt)
    }
  }
  
  /**
   * Return a page of (Speaker,Company).
   *
   * @param page Page to display
   * @param pageSize Number of speakers per page
   * @param orderBy Speaker property used for sorting
   * @param filter Filter applied on the name column
   */
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[(Speaker, Option[Company])] = {
    
    val offest = pageSize * page
    
    DB.withConnection { implicit connection =>
      
      val speakers = SQL(
        """
          select * from speaker 
          left join company on speaker.company_id = company.id
          where speaker.name like {filter}
          order by {orderBy} nulls last
          limit {pageSize} offset {offset}
        """
      ).on(
        'pageSize -> pageSize, 
        'offset -> offest,
        'filter -> filter,
        'orderBy -> orderBy
      ).as(Speaker.withCompany *)

      val totalRows = SQL(
        """
          select count(*) from speaker 
          left join company on speaker.company_id = company.id
          where speaker.name like {filter}
        """
      ).on(
        'filter -> filter
      ).as(scalar[Long].single)

      Page(speakers, page, offest, totalRows)
      
    }
    
  }
  
  /**
   * Update a speaker.
   *
   * @param id The speaker id
   * @param speaker The speaker values.
   */
  def update(id: Long, speaker: Speaker) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          update speaker
          set name = {name}, session = {session}, registrated = {registrated}, company_id = {company_id}
          where id = {id}
        """
      ).on(
        'id -> id,
        'name -> speaker.name,
        'session -> speaker.session,
        'registrated -> speaker.registrated,
        'company_id -> speaker.companyId
      ).executeUpdate()
    }
  }
  
  /**
   * Insert a new speaker.
   *
   * @param speaker The speaker values.
   */
  def insert(speaker: Speaker) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into speaker values (
            (select next value for speaker_seq), 
            {name}, {session}, {registrated}, {company_id}
          )
        """
      ).on(
        'name -> speaker.name,
        'session -> speaker.session,
        'registrated -> speaker.registrated,
        'company_id -> speaker.companyId
      ).executeUpdate()
    }
  }
  
  /**
   * Delete a speaker.
   *
   * @param id Id of the speaker to delete.
   */
  def delete(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("delete from speaker where id = {id}").on('id -> id).executeUpdate()
    }
  }
  
}

object Company {
    
  /**
   * Parse a Company from a ResultSet
   */
  val simple = {
    get[Option[Long]]("company.id") ~
    get[String]("company.name") map {
      case id~name => Company(id, name)
    }
  }
  
  /**
   * Construct the Map[String,String] needed to fill a select options set.
   */
  def options: Seq[(String,String)] = DB.withConnection { implicit connection =>
    SQL("select * from company order by name").as(Company.simple *).
      foldLeft[Seq[(String, String)]](Nil) { (cs, c) => 
        c.id.fold(cs) { id => cs :+ (id.toString -> c.name) }
      }
  }
  
}

