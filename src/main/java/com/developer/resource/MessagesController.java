package com.developer.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessagesController {
	
	private static final String MESSAGE_ATTRIBUTE_NAME = "message";
	private static final String MESSAGES_ATTRIBUTE_NAME = "messages";
	private static final String MESSAGE_TYPE_ATTRIBUTE_NAME = "messageType";
	private static final String USERS_ATTRIBUTE_NAME = "users";
	private static final String MESSAGE_TYPE_INBOX = "Inbox";
	private static final String MESSAGE_TYPE_SENT = "Sent";
	
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	@GetMapping("/inbox")
	public String inbox(Map<String, Object> model ) {
		
		String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		String sql = "select * from message a where a.to_id = :to_id";
		List<Message> messages = jdbc.query(sql, new MapSqlParameterSource("to_id", username), new MsgRowMapper());
		model.put(MESSAGES_ATTRIBUTE_NAME, messages);
		model.put(MESSAGE_TYPE_ATTRIBUTE_NAME, MESSAGE_TYPE_INBOX);
		return "message-list";
	}
	
	@GetMapping("/sent")
	public String sent(Map<String, Object> model ) {
		
		String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		String sql = "select * from message a where a.from_id = :from_id";
		List<Message> messages = jdbc.query(sql, new MapSqlParameterSource("from_id", username), new MsgRowMapper());
		model.put(MESSAGES_ATTRIBUTE_NAME, messages);
		model.put(MESSAGE_TYPE_ATTRIBUTE_NAME, MESSAGE_TYPE_SENT);
		return "message-list";
	}
	
	
	@PostMapping("/{id}")
	public String delete(@PathVariable Long id, Map<String, Object> model ) {
		
		String sql = "delete from message where id = :id";
		jdbc.update(sql, new MapSqlParameterSource("id", id));
		return "redirect:/messages/inbox";
	}
	
	@GetMapping("/compose")
	public String compose(Map<String, Object> model) {
		String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		String sql = "select * from message a where a.to_id = :to_id";
		List<Message> users = jdbc.query(sql,new MapSqlParameterSource("to_id", username) , new MsgRowMapper());
		
		
		model.put(USERS_ATTRIBUTE_NAME, users);
		model.put(MESSAGE_ATTRIBUTE_NAME, new Message());
		return "message-compose";		
	}
	
	@PostMapping
	public String save(	@Valid Message message) {
		
		String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		message.setFromId(username);
		String sql = "insert into message (created,to_id,from_id,summary,text) values(:created,:to_id, :from_id,:summary, :text)";
		jdbc.update(sql, new MapSqlParameterSource("created", LocalDateTime.now())
				.addValue("to_id", message.getToId())
				.addValue("from_id", message.getFromId())
				.addValue("summary", message.getSummary())
				.addValue("text", message.getText())
				);
		return "redirect:/messages/sent";
	}

}

class MsgRowMapper implements RowMapper<Message>{
	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message msg = new Message();
		msg.setId(rs.getLong("id"));
		msg.setCreated(rs.getObject("created", LocalDateTime.class));
		msg.setFromId(rs.getString("from_id"));
		msg.setToId(rs.getString("to_id"));
		msg.setSummary(rs.getString("summary"));
		msg.setText(rs.getString("text"));
		return msg;
	}
}

