package com.ratiose.testtask.service.tmdb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ratiose.testtask.dto.MovieDto;
import com.ratiose.testtask.dto.SearchMovieDataDto;
import com.ratiose.testtask.service.UserService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TmdbApiImpl implements TmdbApi {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    @Autowired
    private UserService userService;

    @Override
    public String popularMovies() {
        Map<String, String> params = Collections.emptyMap();
        String url = "/movie/popular";
        return getResponseBodyFromTmdb(params, url);
    }

    @Override
    public List<MovieDto> getUnwatchedMovies(Set<String> actorsIds, String year, String month) {
        if (actorsIds.isEmpty()) {
            return null;
        }
        try {
            List<MovieDto> moviesTotalResult = getMoviesTotalResult(actorsIds, prepareParams(1, actorsIds));
            Set<Integer> watchedMoviesIds = userService.getCurrentUser().getWatchedMoviesIds();
            String date = year + "-" + month;
            return moviesTotalResult.stream()
                    .filter(movieDto -> !watchedMoviesIds.contains(movieDto.getId()))
                    .filter(movieDto -> movieDto.getRelease_date().startsWith(date))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<MovieDto> getMoviesTotalResult(Set<String> actorsIds, Map<String, String> params) throws IOException {
        String url = "/discover/movie";
        ObjectMapper mapper = new ObjectMapper();
        SearchMovieDataDto searchMovieDataDto = mapper.readValue(getResponseBodyFromTmdb(params, url), SearchMovieDataDto.class);
        List<MovieDto> result = new ArrayList<>(searchMovieDataDto.getResults());
        int currentPage = 1;
        int totalPages = searchMovieDataDto.getTotal_pages();
        while (currentPage++ <= totalPages) {
            searchMovieDataDto = mapper.readValue(getResponseBodyFromTmdb(prepareParams(currentPage, actorsIds), url), SearchMovieDataDto.class);
            result.addAll(searchMovieDataDto.getResults());
        }
        return result;
    }

    private Map<String, String> prepareParams(int page, Set<String> actorsIds) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("with_people", StringUtils.join(actorsIds, '|'));
        return params;
    }

    private String getResponseBodyFromTmdb(Map<String, String> params, String url) {
        try {
            url = getTmdbUrl(url, params);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();

            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return null;
            }
            return jsonResponse.getBody().toString();
        } catch (UnirestException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTmdbUrl(String tmdbItem, Map<String, String> params) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = new URIBuilder(builder.toString());
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        params.forEach(uriBuilder::addParameter);
        return uriBuilder.build().toString();
    }
}
