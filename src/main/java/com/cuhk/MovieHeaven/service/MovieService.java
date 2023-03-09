package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.dao.MovieMapper;
import com.cuhk.MovieHeaven.entity.Movie;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    MovieMapper movieMapper;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    private LoadingCache<String,List<Movie>> movieListCaffeineCache;

    private LoadingCache<Object,Integer> movieRowsCaffeineCache;


    @PostConstruct
    public void init(){

        movieListCaffeineCache= Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<Movie>>() {
                    @Override
                    public @Nullable List<Movie> load(@NonNull String s) throws Exception {
                        if(s==null||s.length()==0){
                            throw new IllegalArgumentException("参数错误");
                        }
                        String[] params = s.split(":");
                        if(params==null||params.length!=2){
                            throw new IllegalArgumentException("参数错误");
                        }
                        int offset=Integer.valueOf(params[0]);
                        int limit =Integer.valueOf(params[1]);

                        logger.debug("load movie lsit from DB");
                        return movieMapper.selectAllMovies(offset,limit);
                    }
                });

        movieRowsCaffeineCache=Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds,TimeUnit.SECONDS)
                .build(new CacheLoader<Object, Integer>() {
                           @Override
                           public @Nullable Integer load(@NonNull Object o) throws Exception {
                               return movieMapper.selectMovieRows();
                           }
                       }
                );
    }

    public List<Movie> findAllMovies(int offset,int limit){
        return movieListCaffeineCache.get(offset+":"+limit);
    }

    public int findMovieRows(){return movieRowsCaffeineCache.get(1)>0?movieRowsCaffeineCache.get(1).intValue():0;}

    public Movie findMovieByMovieId(int movieId){
        return movieMapper.selectMovieByMovieId(movieId);
    }

    public Movie findMovieByMovieTitle(String title){
        return movieMapper.selectMovieByTitle(title);
    }

    public int addMovie(Movie movie){
        if(movie==null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        movie.setInfo(HtmlUtils.htmlEscape(movie.getInfo()));
        return movieMapper.insertMovie(movie);
    }

    public int update(int movieId,int rating){
        return movieMapper.updateRating(movieId,rating);
    }
}
