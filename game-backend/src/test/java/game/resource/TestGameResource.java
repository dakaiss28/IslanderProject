package game.resource;


import com.github.hanleyt.JerseyExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import game.model.Map;
import game.model.MapImpl;
import game.model.Score;
import game.model.Storage;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestGameResource {
	static {
		System.setProperty("jersey.config.test.container.port", "0");
	}

	@RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	Storage data;

	static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

	Application configureJersey() {
		data = new Storage();
        //data = Mockito.mock(Storage.class);
		return new ResourceConfig(GameResource.class)

				.register(MyExceptionMapper.class)
				.register(JacksonFeature.class)
				.register(new AbstractBinder() {
					@Override
					protected void configure() {
						bind(data).to(Storage.class);
					}
				});
	}


	<T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
		res.bufferEntity();
		final String json = res.readEntity(String.class);
		log.log(Level.INFO, "JSON received: " + json);
		final T obj = res.readEntity(classToRead);
		res.close();
		return obj;
	}

	@BeforeEach
	void init() throws IOException {
		File mapFile = data.getMapFile();
		FileWriter out = new FileWriter(mapFile);
		out.write("[]");
		out.close();

		data.setMapFileandUpdateList(mapFile);
	}


	@Test
	void testPostMap(final WebTarget target){


		Response responseAfterPost = target
				.path("game/map/testMap")
				.request()
				.post(Entity.text(""));
		// This Response object provides a status that can be checked (see the HTTP header status picture in the subject).
		assertEquals(Response.Status.OK.getStatusCode(), responseAfterPost.getStatus());
		// The Response object may also embed an object that can be read (give the expected class as parameter).
		Map expectedMap = LogJSONAndUnmarshallValue(responseAfterPost,MapImpl.class);
		// The two Map instances must be equals.
		assertEquals("testMap", expectedMap.getMapName());
	}


	@Test
	void testPostScore(final WebTarget target) {
		Response responseMapPost = target
				.path("game/map/testMap")
				.request()
				.post(Entity.text(""));

		Response responseAfterPost = target
				.path("game/score/testMap/player/10")
				.request()
				.post(Entity.text(""));
		assertEquals(Response.Status.OK.getStatusCode(),responseAfterPost.getStatus());
		Score newScore = LogJSONAndUnmarshallValue(responseAfterPost,Score.class);
		assertEquals(newScore.getScore(),10);
		assertEquals("player",newScore.getPlayer());
	}


	@Test
	void testGetMaps(final WebTarget target) throws IOException {
		Response responseMapPost = target
				.path("game/map/map1")
				.request()
				.post(Entity.text(""));
		Response responseMapPost2 = target
				.path("game/map/map2")
				.request()
				.post(Entity.text(""));

		Map map1 = LogJSONAndUnmarshallValue(responseMapPost,Map.class);
		Map map2 = LogJSONAndUnmarshallValue(responseMapPost2,Map.class);

		data.addMap(map1);
		data.addMap(map2);

		Response responseAfterPost = target
				.path("game/map")
				.request()
				.get();
		assertEquals(Response.Status.OK.getStatusCode(), responseAfterPost.getStatus());
		List<Map> list = responseAfterPost.readEntity(new GenericType<>() {});
		assertEquals(map1.getMapName(),list.get(0).getMapName());
		assertEquals(map2.getMapName(),list.get(1).getMapName());
	}


	@Test
	void testGetScores(final WebTarget target) throws IOException {
		Response responseMapPost = target
				.path("game/map/map1")
				.request()
				.post(Entity.text(""));

		Response responseScorePost = target
				.path("game/score/map1/player/10")
				.request()
				.post(Entity.text(""));

		Response responseAfterPost = target
				.path("game/score/map1")
				.request()
				.get();

		Map map1 = LogJSONAndUnmarshallValue(responseMapPost, Map.class);
		map1.postNewScore("player",10);
		data.addMap(map1);

		assertEquals(Response.Status.OK.getStatusCode(),responseScorePost.getStatus());
		assertEquals(Response.Status.OK.getStatusCode(),responseAfterPost.getStatus());
		List<Score> liste = responseAfterPost.readEntity(new GenericType<>() {});
		assertEquals("player", liste.get(0).getPlayer());
		assertEquals(10,liste.get(0).getScore());
		assertEquals(data.getMap("map1").getScores(), liste);
	}

	@Test
	void testGetScoreAlreadyExistingMap (final WebTarget target) throws IOException {

		File mapFile = data.getMapFile();
		FileWriter out = new FileWriter(mapFile);
		out.write("[{\"type\":\"game.model.MapImpl\",\"mapName\":\"providedMap1\",\"tiles\":[{\"occupied\":true,\"x\":0,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":1,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":4,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":0,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":0,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":1,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":2,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":1,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":1,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":2,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":2,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":3,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":3,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":3,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":1,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":4,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":4,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":5,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":3,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":4,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":6,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":7,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":5,\"y\":8,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":5,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":0,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":6,\"y\":2,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":5,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":6,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":8,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":6,\"y\":9,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":1,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":2,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":3,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":4,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":5,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":7,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":7,\"y\":9,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":0,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":8,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":5,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":7,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":8,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":8,\"y\":9,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":0,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":1,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":2,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":3,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":4,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":5,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":6,\"type\":\"WATER\",\"block\":\"NOTHING\"},{\"occupied\":false,\"x\":9,\"y\":7,\"type\":\"GRASS\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":8,\"type\":\"TREE\",\"block\":\"NOTHING\"},{\"occupied\":true,\"x\":9,\"y\":9,\"type\":\"TREE\",\"block\":\"NOTHING\"}],\"scores\":[]}]");
		out.close();

		data.setMapFileandUpdateList(mapFile);

		Response responseAfterGet = target
				.path("game/score/providedMap1")
				.request()
				.get();

		assertEquals(Response.Status.OK.getStatusCode(),responseAfterGet.getStatus());
		List<Score> liste = responseAfterGet.readEntity(new GenericType<>() {});
		assertEquals("player6", liste.get(0).getPlayer());
		assertEquals(200,liste.get(0).getScore());

		out = new FileWriter(mapFile);
		out.write("[]");
		out.close();
	}

	// Example of a route test. The one for getting a list of available maps

	@Test
	void testGetNames(final Client client, final URI baseUri) throws IOException {
		Response responseMapPost = client
				.target(baseUri)
				.path("game/map/map1")
				.request()
				.post(Entity.text(""));
		Response responseMapPost2 = client
				.target(baseUri)
				.path("game/map/map2")
				.request()
				.post(Entity.text(""));

		final Response res = client
			.target(baseUri)
			.path("game/map")
				.request()
			.get();

		data.addMap(LogJSONAndUnmarshallValue(responseMapPost,Map.class));
		data.addMap(LogJSONAndUnmarshallValue(responseMapPost2,Map.class));

  	  assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
		final List<Map> mapsRegistred = res.readEntity(new GenericType<>() {});
		final List<String> names = mapsRegistred
				.stream()
				.map(Map::getMapName)
				.collect(Collectors.toList());

		final List<String> mapNames = data.getMaps()
				.stream()
				.map(Map::getMapName)
				.collect(Collectors.toList());


		assertEquals(names,mapNames);

	}

}
