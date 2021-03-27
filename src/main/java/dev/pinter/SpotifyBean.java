package dev.pinter;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("spotifyBean")
@ViewScoped
public class SpotifyBean implements Serializable {
    private final SpotifyService spotifyService = new SpotifyService();
    private String albumId;
    private boolean rendered = false;
    private List<Album> albums = new ArrayList<>(100000);
    private String searchArtistName;
    private SearchRoot response;
    private List<SearchItem> artists = new ArrayList<>(100000);
    private List<ArtistAlbumsItem> artistAlbumsList = new ArrayList<>(100000);

    public String getSearchArtistName() {
        return searchArtistName;
    }

    public void setSearchArtistName(String searchArtistName) {
        this.searchArtistName = searchArtistName;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public void toggleRendered() {
        this.rendered = !rendered;
    }

    public SearchRoot getResponse() {
        return response;
    }

    public void setResponse(SearchRoot response) {
        this.response = response;
    }

    public List<SearchItem> getArtists() {
        return artists;
    }

    public void setArtists(List<SearchItem> artists) {
        this.artists = artists;
    }

    public List<ArtistAlbumsItem> getArtistAlbumsList() {
        return artistAlbumsList;
    }

    public void setArtistAlbumsList(List<ArtistAlbumsItem> artistAlbumsList) {
        this.artistAlbumsList = artistAlbumsList;
    }

    public void requestAlbum(ActionEvent event) {
//        5PORx6PL7CdOywSJuGVrnc
        setRendered(true);
        AccessTokenResponse accessToken = spotifyService.getAccesssToken();
        if (accessToken == null) {
            throw new NotAuthorizedException("Error: invalid token");
        }
        System.out.println(accessToken);
        System.out.println(albumId);
        if (albumId.length() == 0) {
            throw new RuntimeException("Invalid id");
        }
        albums.add(spotifyService.getAlbum(accessToken.getAccessToken(), albumId));
    }

    public void requestArtistAlbums(String at, String id) {
        ArtistAlbumsRoot aar = spotifyService.getArtistAlbums(at, id);
        artistAlbumsList = aar.getItemsList();
        System.out.println(aar);
    }

    public void searchArtist(ActionEvent event) {
        String at = spotifyService.getAccesssToken().getAccessToken();
        response = spotifyService.searchArtist(searchArtistName, at);
        SearchItem si = response.getSearchArtist().getItems().get(0);
        String id = si.getId();
        requestArtistAlbums(at, id);
        for (int i = 0; i <= artistAlbumsList.size() - 1; i++) {
            if (artistAlbumsList.get(i).getName().equals(artistAlbumsList.get(i + 1).getName()) ||
                    artistAlbumsList.get(i).getId().equals(artistAlbumsList.get(i + 1).getId()) ||
                    artistAlbumsList.get(i).getReleaseDate().equals(artistAlbumsList.get(i + 1).getReleaseDate()) ||
                    artistAlbumsList.get(i).getTotalTracks() == artistAlbumsList.get(i + 1).getTotalTracks()) {
                artistAlbumsList.remove(i + 1);
            }
        }
    }

    public String strList(List<String> genres) {
        return genres.toString().replace("[", "").replace("]", "");
    }
}
