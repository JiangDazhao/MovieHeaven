package com.cuhk.MovieHeaven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cuhk.MovieHeaven.config.NettyConfig;
import com.cuhk.MovieHeaven.server.NettyServer;

@SpringBootApplication
public class MovieHeavenApplication implements CommandLineRunner {

	@Autowired
	private NettyConfig nettyConfig;

	@Autowired
	private NettyServer nettyServer;

	public static void main(String[] args) {
		SpringApplication.run(MovieHeavenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				nettyServer.start(nettyConfig.getPort());
			}
		}).start();
	}

}
