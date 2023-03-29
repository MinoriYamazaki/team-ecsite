package jp.co.internous.plum.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.plum.model.domain.MstDestination;

@Mapper
public interface MstDestinationMapper {
    
    // ユーザーIDをもとに宛先情報を取得する
    @Select("SELECT * FROM mst_destination WHERE user_id = #{userId} AND status = 1")
    List<MstDestination> findByUserId(int userId);

    // 宛先情報を新規登録する
    @Insert("INSERT INTO mst_destination ("
            + "user_id, family_name, first_name, tel_number, address"
            + ") VALUES ("
            + "#{userId}, #{familyName}, #{firstName}, #{telNumber}, #{address}"
            + ")")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insert(MstDestination destination);
    
    // 宛先情報を削除する
    @Update("UPDATE mst_destination SET status = 0, updated_at = NOW() WHERE id = #{id}")
    int logicalDeleteById(int id);
    
}