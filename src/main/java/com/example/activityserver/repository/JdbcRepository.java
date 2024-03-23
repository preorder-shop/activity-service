package com.example.activityserver.repository;

import com.example.activityserver.domain.dto.response.PostDto;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 뉴스피드에서 조건별 게시글 조회 API
     */
    public List<PostDto> getPostListByCondition(String userId, String type,int startPage,int pageSize) { // 인기순으로 정렬(좋아요 개수)
        String sql = "";
        int offset = (startPage)*pageSize;
        if (type.equals("all")) { // 모든 포스트
            sql = "select p.id, p.title, p.content , p.user_id from post p "
                    + "left join (select post_id ,count(*) as like_count from like_post group by post_id) lp on lp.post_id=p.id "
                    + "order by lp.like_count DESC "
                    + "LIMIT ? OFFSET ?";

            Object[] args = new Object[]{pageSize,offset};
            return jdbcTemplate.query(sql, itemRowMapper(),args);
        }
        if (type.equals("follow")) {//  팔로우한 사용자의 포스트
            sql = "select p.id, p.title, p.content , p.user_id from post p "
                    + "left join (select post_id ,count(*) as like_count from like_post group by post_id) lp on lp.post_id=p.id "
                    + "left join (select to_user_id, from_user_id from follow) f on f.from_user_id=? "
                    + "where p.user_id=f.to_user_id "
                    + "order by lp.like_count DESC "
                    + "LIMIT ? OFFSET ?";

            Object[] args = new Object[]{userId,pageSize,offset};

            return jdbcTemplate.query(sql, itemRowMapper(), args);
        }
        return null;

    }

//
//    public List<MyPostAlarm> getMyPostAlarm(String userId) {
//        List<String> activeTypes = Arrays.asList(ActiveType.CANCEL_LIKE_POST.toString(),ActiveType.LIKE_POST.toString(),ActiveType.WRITE_COMMENT.toString());
//        String placeHolders = String.join(",", new String[activeTypes.size()]);
//
//        String sql = "select postId,  ul.actor, ul.activeType,createdDateTime  from UserLog ul "
//                + "left join (select id as postId , userId from Post) p on p.userId=? "
//                + "where ul.actor = f.fromUserId and "
//                + "where ul.activeType=? or ul.activeType=? or ul.activeType=? "
//                + "order by ul.createdDateTime DESC";
//
//        return jdbcTemplate.query(sql, itemRowMapper2(), userId, userId
//                ,
//                ActiveType.CANCEL_LIKE_POST.toString(),ActiveType.LIKE_POST.toString(),
//                ActiveType.WRITE_COMMENT.toString()
//        );
//
//    }

//    private RowMapper<MyPostAlarm> itemRowMapper2() { // table 에서 가져온 정보를 객체에 mapping
//        return ((rs, rowNum) -> {
//            MyPostAlarm postAlarm = new MyPostAlarm();
//            postAlarm.setPostId(rs.getString("postId"));
//            postAlarm.setActor(rs.getString("actor"));
//            postAlarm.setActiveType(rs.getString("activeType"));
//            return postAlarm;
//        });
//    }

    private RowMapper<PostDto> itemRowMapper() { // table 에서 가져온 정보를 객체에 mapping
        return ((rs, rowNum) -> {
            PostDto post = PostDto
                    .builder()
                    .postId(rs.getLong("id"))
                    .writer(rs.getString("user_id"))
                    .title(rs.getString("title"))
                    .content(rs.getString("content"))
                    .build();
            return post;
        });
    }
}
