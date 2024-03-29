package com.cuhk.MovieHeaven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cuhk.MovieHeaven.config.NettyConfig1;
import com.cuhk.MovieHeaven.config.NettyConfig2;
import com.cuhk.MovieHeaven.server.NettyServer;

@SpringBootApplication
public class MovieHeavenApplication implements CommandLineRunner {

	@Autowired
	private NettyConfig1 nettyConfig1;

	@Autowired
	private NettyConfig2 nettyConfig2;

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
				nettyServer.start(nettyConfig1.getPort(), nettyConfig1.getPath());
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				nettyServer.start(nettyConfig2.getPort(), nettyConfig2.getPath());
			}
		}).start();
	}

}