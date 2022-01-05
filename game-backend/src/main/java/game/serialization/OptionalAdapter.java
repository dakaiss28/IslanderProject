package game.serialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.model.Map;
import game.model.MapImpl;
import game.model.Score;
import java.io.File;
import java.io.IOException;
import java.util.List;


public final class OptionalAdapter {

	private OptionalAdapter() {
		super();
	}

	/**
	 *
	 * @param monObjet the object to marshall ( a list of maps )
	 * @return the JSON object as a String
	 * @throws JsonProcessingException if problem
	 */
	public static String fromListMapToJSON(final Object monObjet) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.writerFor(new TypeReference<List<MapImpl>>() { }).writeValueAsString(monObjet);
	}

	/**
	 *
	 * @param monObjet the object to marshall ( a list of scores)
	 * @return the JSON object as a String
	 * @throws JsonProcessingException if problem
	 */
	public static String fromListScoreToJSON(final Object monObjet) throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.writerFor(new TypeReference<List<Score>>() { }).writeValueAsString(monObjet);
	}


	/**
	 *
	 * @param text th file with the maps
	 * @return an unmarshalled list of maps
	 * @throws IOException if problem with map File
	 */
	public static List<game.model.Map> fromJSONtoMaps(final File text) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(text, new TypeReference<List<Map>>() { });

	}

	/**
	 *
	 * @param text th file with the scores of a certain map
	 * @return an unmarshalled list of scores
	 * @throws IOException if problem with score File
	 */
	public static List<Score> fromJSONtoScores(final File text) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(text, new TypeReference<List<Score>>() { });

	}

}


