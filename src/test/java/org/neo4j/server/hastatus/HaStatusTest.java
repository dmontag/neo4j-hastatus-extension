/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.hastatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.kernel.HighlyAvailableGraphDatabase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith( MockitoJUnitRunner.class )
public class HaStatusTest
{
    @Mock
    private HighlyAvailableGraphDatabase haDbStub;

    @Test
    public void shouldReturn200ByDefaultOnSuccess() throws Exception
    {
        when( haDbStub.isMaster() ).thenReturn( true );
        assertEquals( 200, new HaStatus( haDbStub ).getStatus( "master" ).getStatus() );
    }

    @Test
    public void shouldReturn404ByDefaultOnFailure() throws Exception
    {
        when( haDbStub.isMaster() ).thenReturn( true );
        assertEquals( 404, new HaStatus( haDbStub ).getStatus( "slave" ).getStatus() );
    }

    @Test
    public void shouldHandleReverseSuccessCase() throws Exception
    {
        when( haDbStub.isMaster() ).thenReturn( false );
        assertEquals( 404, new HaStatus( haDbStub ).getStatus( "master" ).getStatus() );
    }

    @Test
    public void shouldHandleReverseFailureCase() throws Exception
    {
        when( haDbStub.isMaster() ).thenReturn( false );
        assertEquals( 200, new HaStatus( haDbStub ).getStatus( "slave" ).getStatus() );
    }

    @Test
    public void shouldReturnSpecifiedSuccessCode() throws Exception
    {
        when( haDbStub.isMaster() ).thenReturn( true );
        assertEquals( 204, new HaStatus( haDbStub ).getStatus( "master", 204, 503 ).getStatus() );
    }

    @Test
    public void shouldReturnSpecifiedFailureCode() throws Exception
    {
        when( haDbStub.isMaster() ).thenReturn( false );
        assertEquals( 503, new HaStatus( haDbStub ).getStatus( "master", 204, 503 ).getStatus() );
    }
}
