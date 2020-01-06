package com.lasgis.kotlin.demo.web.mybatis

import com.lasgis.kotlin.demo.web.dao.UserRole
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.TypeHandler
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class RoleTypeHandlers : TypeHandler<UserRole> {

    override fun setParameter(ps: PreparedStatement?, i: Int, parameter: UserRole?, jdbcType: JdbcType?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResult(rs: ResultSet?, columnName: String?): UserRole {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResult(rs: ResultSet?, columnIndex: Int): UserRole {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResult(cs: CallableStatement?, columnIndex: Int): UserRole {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}