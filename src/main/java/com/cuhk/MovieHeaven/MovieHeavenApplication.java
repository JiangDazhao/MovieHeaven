package com.cuhk.MovieHeaven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cuhk.MovieHeaven.client.NettyClient;
import com.cuhk.MovieHeaven.config.NettyConfig;
import com.cuhk.MovieHeaven.pojo.NettyMessage;
import com.cuhk.MovieHeaven.server.NettyServer;

@SpringBootApplication
public class MovieHeavenApplication implements CommandLineRunner {

	@Autowired
	private NettyConfig nettyConfig;

	@Autowired
	private NettyServer nettyServer;

	@Autowired
	private NettyClient nettyClient;

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

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("!!!!!!!!!!sending!!!!!!!!");
				NettyMessage msg = new NettyMessage("noidea", "137", "hello", 0);
				nettyClient.sendMsg(msg);
			}
		}).start();
	}

}
