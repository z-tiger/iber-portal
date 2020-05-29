package com.iber.portal.enums.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntEnumTypeHandler  <E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<IntEnum> {
    private Class<E> type;

    public IntEnumTypeHandler(){
        super();
    }
    public IntEnumTypeHandler(Class<E> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    private E convert(int status) {
        IntEnum[] objs = type.getEnumConstants();
        for (IntEnum em : objs) {
            if (em.getIntValue() == status) {
                return (E) em;
            }
        }
        return null;
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    IntEnum enumObj, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, enumObj.getIntValue());

    }
}
