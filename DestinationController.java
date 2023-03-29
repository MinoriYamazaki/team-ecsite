package jp.co.internous.plum.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.plum.model.domain.MstDestination;
import jp.co.internous.plum.model.domain.MstUser;
import jp.co.internous.plum.model.mapper.MstDestinationMapper;
import jp.co.internous.plum.model.mapper.MstUserMapper;
import jp.co.internous.plum.model.session.LoginSession;
import jp.co.internous.plum.model.form.DestinationForm;

@Controller
@RequestMapping("/plum/destination")
public class DestinationController {

	@Autowired
	private LoginSession loginSession;

	@Autowired
	private MstUserMapper userMapper;

	@Autowired
	private MstDestinationMapper destinationMapper;

	@RequestMapping("/")
	public String Destination(Model model) {

		// ログインしている場合はユーザー情報を表示
		MstUser user = userMapper.findByUserNameAndPassword(loginSession.getUserName(),loginSession.getPassword());
		
		model.addAttribute("user", user);
		// page_header.htmlでsessionの変数を表示させているため、loginSessionも画面に送る。
		model.addAttribute("loginSession", loginSession);
		
		return "destination";
	}

	@ResponseBody
	@RequestMapping("/delete")
	public String delete(@RequestBody Map<String, String> requestData) {

		// ラジオボタンで選択されたIDはString型で受け取るので変換
		String destinationId = requestData.get("destinationId");
		int id = Integer.parseInt(destinationId);

		// 論理削除処理
		destinationMapper.logicalDeleteById(id);

		return "success";

	}

	@ResponseBody
	@RequestMapping("/register")
	public String register(@RequestBody DestinationForm f) {

		// 宛先を登録
		MstDestination destination = new MstDestination(f);
		int userId = loginSession.getUserId();

		// 宛先情報をDBに登録する
		destination.setUserId(userId);
		int count = destinationMapper.insert(destination);

		// 登録した宛先のIDを取得
		Integer id = 0;
		if (count > 0) {
			id = destination.getId();
		}

		return id.toString();
	}

}
