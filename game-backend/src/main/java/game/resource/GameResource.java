package game.resource;


import game.model.Map;
import game.model.MapImpl;
import game.model.Storage;
import game.model.Score;
import io.swagger.annotations.Api;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Singleton
@Path("game")
@Api(value = "game")

public class GameResource {
	Storage maps;
	public GameResource() {
		super();
		this.maps = new Storage();
	}


	/** [R23_2_REST_MAP]
	 *
	 * @return returns all Maps stored in the Map file
	 * @throws IOException if problem is detected in Map file
	 */
	@GET
	@Path("/map")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaps() throws IOException {
		try {
			return Response.status((Response.Status.OK)).entity(new GenericEntity<>(maps.getMaps()) {
			}).build();
		} catch (final IllegalArgumentException ex) {
			return new MyExceptionMapper().toResponse(new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, ex.getMessage()).build()));
		}
	}


	/** [R23_3_REST_GET_SCORE]
	 *
	 * @param mapName the name of the Map from which we want the scores
	 * @return top 5 scores achieved on the map
	 * @throws IOException if problems detected on score File
	 */
	@GET
	@Path("/score/{map}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Score> getScores(@PathParam("map") final String mapName) throws IOException {

		final Map carte = maps.getMap(mapName);
		final List<Score> scores = carte
				.getScores()
				.stream()
				.sorted(Comparator.comparingInt(Score::getScore).reversed())
				.limit(5)
				.collect(Collectors.toList());
		return scores;

	}


	/**[R23_4_REST_ADD_SCORE]
	 *
	 * @param mapName : the Name of the Map
	 * @param player : the Player who played on mapName
	 * @param newScore : the score reached by the player while playing
	 * @return Response to check if the score got posted successfully
	 */
	@POST
	@Path("/score/{map}/{player}/{newScore}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postScore(@PathParam("map") final String mapName, @PathParam("player") final String player, @PathParam("newScore") final int newScore)  {
		final Map map = maps.getMap(mapName);
		try {
			map.postNewScore(player, newScore);
			return Response.status(Response.Status.OK).entity(new GenericEntity<>(new Score(player, newScore)) { }).build();
		} catch(final IllegalArgumentException | IOException ex) {
			return new MyExceptionMapper().toResponse(new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, ex.getMessage()).build()));
		}
	}

	/**
	 * [R23_5_NEW_RANDOM_MAP] (BONUS)
	 * [R23_6_NEW_MAP] (BONUS)
	 *
	 * @param newMap the name of the Map that we be randomly initialized and stored
	 * @return Response to check if it was a success
	 */
	@POST
	@Path("/map/{newMap}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMap(@PathParam("newMap") final String newMap) {
		final Map map = new MapImpl(newMap);
		try {
			maps.addMap(map);
			return Response.status(Response.Status.OK).entity(new GenericEntity<>(map) { }).build();
		}catch(final IllegalArgumentException ex) {
			return new MyExceptionMapper().toResponse(new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, ex.getMessage()).build()));
		}
	 }

	/** [R23_2_REST_MAP]
	 *
	 * @return returns a new random Map
	 * @throws IOException if problem is detected in Map file
	 */

	@GET
	@Path("/random")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRandomMap() throws IOException {
		final Map map = new MapImpl(randomName());
		try {
			maps.addMap(map);
			return Response.status(Response.Status.OK).entity(new GenericEntity<>(map) { }).build();
		}catch(final IllegalArgumentException ex) {
			return new MyExceptionMapper().toResponse(new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST, ex.getMessage()).build()));
		}
	}

	/**
	 *
	 * @return a random Name for a Map
	 */
	public String randomName() {

		final int leftLimit = 97; // letter 'a'
		final int rightLimit = 122; // letter 'z'
		final int targetStringLength = 6;
		final Random random = new Random();


		return random.ints(leftLimit, rightLimit + 1)
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}




}
