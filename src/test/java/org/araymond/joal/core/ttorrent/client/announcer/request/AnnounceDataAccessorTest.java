package org.araymond.joal.core.ttorrent.client.announcer.request;

import com.turn.ttorrent.common.protocol.TrackerMessage;
import com.turn.ttorrent.common.protocol.TrackerMessage.AnnounceRequestMessage.RequestEvent;
import org.araymond.joal.core.bandwith.BandwidthDispatcher;
import org.araymond.joal.core.bandwith.TorrentSeedStats;
import org.araymond.joal.core.client.emulated.BitTorrentClient;
import org.araymond.joal.core.torrent.torrent.InfoHash;
import org.araymond.joal.core.torrent.torrent.InfoHashTest;
import org.araymond.joal.core.ttorrent.client.ConnectionHandler;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

public class AnnounceDataAccessorTest {

    @Test
    public void shouldCallCreateQueryOnBitTorrentClient() {
        final InfoHash infoHash = InfoHashTest.createOne("abc");
        final RequestEvent event = RequestEvent.STARTED;

        final BitTorrentClient bitTorrentClient = mock(BitTorrentClient.class);
        final BandwidthDispatcher bandwidthDispatcher = mock(BandwidthDispatcher.class);
        doReturn(new TorrentSeedStats()).when(bandwidthDispatcher).getSeedStatForTorrent(Matchers.eq(infoHash));
        final ConnectionHandler connectionHandler = mock(ConnectionHandler.class);

        final AnnounceDataAccessor announceDataAccessor = new AnnounceDataAccessor(bitTorrentClient, bandwidthDispatcher, connectionHandler);

        announceDataAccessor.getHttpRequestQueryForTorrent(infoHash, event);

        verify(bitTorrentClient, times(1)).createRequestQuery(
                Matchers.eq(event),
                Matchers.eq(infoHash),
                Matchers.any(TorrentSeedStats.class),
                Matchers.eq(connectionHandler)
        );
    }

    @Test
    public void shouldCallCreateHeadersOnBitTorrentClient() {
        final BitTorrentClient bitTorrentClient = mock(BitTorrentClient.class);
        final BandwidthDispatcher bandwidthDispatcher = mock(BandwidthDispatcher.class);
        final ConnectionHandler connectionHandler = mock(ConnectionHandler.class);

        final AnnounceDataAccessor announceDataAccessor = new AnnounceDataAccessor(bitTorrentClient, bandwidthDispatcher, connectionHandler);

        announceDataAccessor.getHttpHeadersForTorrent();

        verify(bitTorrentClient, times(1)).createRequestHeaders();
    }

}
