package utility;

import java.io.IOException;
import java.text.SimpleDateFormat;

import models.Post;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class PostAdapter extends TypeAdapter<Post> {

	@Override
	public Post read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
	         reader.nextNull();
	         return null;
	       }
		//long postid = reader.nextLong();
		return null;
	}

	@Override
	public void write(JsonWriter writer, Post post) throws IOException {
		if (post == null) {
	         writer.nullValue();
	         return;
	       }
		//long postid = post.id;
		String jpost = "{" + "\"postTitle\": " + "\"" + post.postTitle + "\""
				+ "," + "\"postContent\": " + "\"" + StringNeutraliser.wipeQuotes(post.postContent) + "\"" 
				+ "," + "\"postDate\": " + "\"" + 
				(new SimpleDateFormat("E dd/MM/yyyy 'at' hh:mm:ss")).format(post.postDate) + "\"" + "}";
		//writer.value(postid);
		writer.value(jpost);
	}
}
