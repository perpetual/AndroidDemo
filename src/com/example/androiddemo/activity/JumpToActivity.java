package com.example.androiddemo.activity;

import java.net.URI;
import java.net.URL;

import android.content.Intent;
import android.net.Uri;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-7-7		Create		
 * </pre>
 */
public class JumpToActivity extends DemoSuperActivity {

	protected String getTopButtonText() {
		return "Jump";
	}
	
	protected void doTopButtonClick() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
//		intent.setData(	Uri.parse("invoketalkroom://?groupId=37066102_2101818468_1436253697&uuid=37066102&roomId=0&roomKey=0&routeId=727881078"));
		intent.setData(	Uri.parse("invoketalkroom://talk?vtype=0&groupId=37066102_2101818468_1436253697&playId=0&uuid=37066102&roomId=0&roomKey=0&routeId=727881078&memSession=0&hostUuid=37066102&playItemInfo="));
		startActivity(intent);
	};
}

