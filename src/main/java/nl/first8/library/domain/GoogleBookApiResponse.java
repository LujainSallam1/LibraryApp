package nl.first8.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class GoogleBookApiResponse {
    private String kind;
    private int totalItems;
    private List<GoogleBookItem> items;

    public String getKind() {
        return kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<GoogleBookItem> getItems() {
        return items;
    }

    public void setItems(List<GoogleBookItem> items) {
        this.items = items;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public static class GoogleBookItem {
        private String kind;
        private String id;
        private GoogleBookVolumeInfo volumeInfo;

        public GoogleBookVolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(GoogleBookVolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }
// الـ Getters والـ Setters هنا
    }

    public static class GoogleBookVolumeInfo {
        private String title;

        public Date getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(Date publishDate) {
            this.publishDate = publishDate;
        }

        public String getTitle() {
            return title;
        }



        public List<GoogleBookIndustryIdentifier> getIndustryIdentifiers() {
            return industryIdentifiers;
        }

        public void setIndustryIdentifiers(List<GoogleBookIndustryIdentifier> industryIdentifiers) {
            this.industryIdentifiers = industryIdentifiers;
        }



        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }

        private List<String> authors;

        private Date publishDate;
        private List<GoogleBookIndustryIdentifier> industryIdentifiers;

        // الـ Getters والـ Setters هنا
    }

    public static class GoogleBookIndustryIdentifier {
        private String type;
        private String identifier;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        // الـ Getters والـ Setters هنا
    }
}