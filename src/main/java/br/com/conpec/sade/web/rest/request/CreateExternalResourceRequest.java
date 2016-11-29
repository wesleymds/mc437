package br.com.conpec.sade.web.rest.request;

import javax.validation.constraints.NotNull;

/**
 * @author Danilo Valente <danilovalente96@gmail.com>
 */
public class CreateExternalResourceRequest {

    @NotNull
    private String url;

    @NotNull
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateExternalResourceRequest that = (CreateExternalResourceRequest) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreateExternalResourceRequest{" +
            "url='" + url + '\'' +
            ", name=" + name +
            '}';
    }
}
