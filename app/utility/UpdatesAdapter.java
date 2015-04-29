package utility;

import java.io.IOException;
import java.util.ArrayList;

import play.Logger;
import models.Post;
import models.Update;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class UpdatesAdapter extends TypeAdapter<Update> {

	@Override
	public Update read(JsonReader arg0) throws IOException {
	
		return null;
	}

	@Override
	public void write(JsonWriter writer, Update update)
			throws IOException {
		if(update == null) {
			 writer.nullValue();
	         return;
		}
		String name = update.updater.firstName + " " + update.updater.lastName;
		String jUpdates = "{\"updaterId\": \"" + update.updater.id + "\","
				+ " \"updaterName\": \"" + name + "\","
				+ " \"postId\": \"" + update.belong.id + "\","
				+ " \"nTitle\": \"" + update.nTitle + "\","
				+ " \"nContent\": \"" + StringNeutraliser.wipeQuotes(update.nContent) + "\","
				+ " \"date\": \"" + update.realDate.getTime() + "\","
				+ " \"blogId\": \"" + update.belong.blogPostHost.id + "\"}";	
		writer.value(jUpdates);
		Logger.info(jUpdates);
	}

}
