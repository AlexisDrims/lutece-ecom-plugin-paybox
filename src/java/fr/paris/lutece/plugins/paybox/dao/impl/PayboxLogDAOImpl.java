/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.paybox.dao.impl;

import fr.paris.lutece.plugins.paybox.dao.PayboxLogDAO;
import fr.paris.lutece.plugins.paybox.dao.entity.PayboxLogEntity;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The Class PayboxLogDAOImpl.
 *
 * No constraints added so far, every attempt, even with the same order
 * reference creates a new log line and first attempt to delete logs for a given
 * order reference removes every found lines.
 */
public class PayboxLogDAOImpl implements PayboxLogDAO
{
    /** The Constant SQL_QUERY_DELETE. */
    private static final String SQL_QUERY_DELETE = "DELETE FROM paybox_log WHERE order_reference = ?";

    /** The Constant SQL_QUERY_INSERT. */
    private static final String SQL_QUERY_INSERT = "INSERT INTO paybox_log(id, date, order_reference) VALUES (?, ?, ?)";

    /** The Constant SQL_QUERY_NEWPK. */
    private static final String SQL_QUERY_NEWPK = "SELECT max( id ) FROM paybox_log;";

    /** The Constant SQL_QUERY_SELECTALL. */
    private static final String SQL_QUERY_SELECTALL = "SELECT id, date, order_reference FROM paybox_log ORDER BY order_reference DESC";

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.paris.lutece.plugins.paybox.dao.PayboxLogDAO#addLog(fr.paris.lutece
     * .plugins.paybox.dao.entity.PayboxLogEntity,
     * fr.paris.lutece.portal.service.plugin.Plugin)
     */
    @Override
    public void addLog( final PayboxLogEntity payboxLogEntity, final Plugin plugin )
    {
        final DAOUtil daoUtil = new DAOUtil( PayboxLogDAOImpl.SQL_QUERY_INSERT, plugin );
        final Long id = this.newPrimaryKey( plugin );
        payboxLogEntity.setId( id );
        payboxLogEntity.setDate( new Date(  ) );
        daoUtil.setLong( 1, payboxLogEntity.getId(  ) );
        daoUtil.setTimestamp( 2, new Timestamp( payboxLogEntity.getDate(  ).getTime(  ) ) );
        daoUtil.setString( 3, payboxLogEntity.getOrderReference(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.paris.lutece.plugins.paybox.dao.PayboxLogDAO#getAll(fr.paris.lutece
     * .portal.service.plugin.Plugin)
     */
    @Override
    public List<PayboxLogEntity> getAll( final Plugin plugin )
    {
        final List<PayboxLogEntity> ret = new ArrayList<PayboxLogEntity>(  );
        final DAOUtil daoUtil = new DAOUtil( PayboxLogDAOImpl.SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            final PayboxLogEntity payboxLogEntity = new PayboxLogEntity(  );
            payboxLogEntity.setId( daoUtil.getLong( 1 ) );
            payboxLogEntity.setDate( new Date( daoUtil.getTimestamp( 2 ).getTime(  ) ) );
            payboxLogEntity.setOrderReference( daoUtil.getString( 3 ) );

            ret.add( payboxLogEntity );
        }

        daoUtil.free(  );

        return ret;
    }

    /**
     * Generates a new primary key.
     *
     * @param plugin The plugin
     * @return The new primary key
     */
    private Long newPrimaryKey( final Plugin plugin )
    {
        final DAOUtil daoUtil = new DAOUtil( PayboxLogDAOImpl.SQL_QUERY_NEWPK, plugin );
        daoUtil.executeQuery(  );

        Long nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1L;
        }

        nKey = daoUtil.getLong( 1 ) + 1;

        daoUtil.free(  );

        return nKey;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.paris.lutece.plugins.paybox.dao.PayboxLogDAO#removeLog(java.lang.String
     * , fr.paris.lutece.portal.service.plugin.Plugin)
     */
    @Override
    public void removeLog( final String orderReference, final Plugin plugin )
    {
        final DAOUtil daoUtil = new DAOUtil( PayboxLogDAOImpl.SQL_QUERY_DELETE, plugin );
        daoUtil.setString( 1, orderReference );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
