package jdo;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import utils.StringTypeConverter;
import utils.Utils;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class Topic {

    private Integer id;

    @NotBlank(message="Topic must have title")
    private String title;

    @SuppressWarnings("unchecked")
    public Map<String, Object> buildSqlMap(){
        return Utils.OBJECT_MAPPER.convertValue(this, Map.class);
    }

    public Map<String, String> buildRedisMap(){
        return ImmutableMap.<String, String>builder()
                .put("id", StringTypeConverter.fromInteger(id))
                .put("title", title)
                .build();
    }

    public static Topic fromRedisMap(Map<String, String> map){
        if (map == null || map.isEmpty()) {
            return null;
        }
        Topic topic = new Topic();
        topic.setId(StringTypeConverter.toInteger(map.get("id")));
        topic.setTitle(map.get("title"));
        return topic;
        
        
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
