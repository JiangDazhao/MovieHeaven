$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");
	// 信息
	var title = $("#recipient-name").val();
	var info = $("#message-text").val();
	var genre= $("#genre").val();
	var year= $("#year").val();
	// 发送异步请求(POST)
	$.post(
		CONTEXT_PATH + "/moviepost/add",
		{"title":title,"info":info,"genre":genre,"year":year},
		function(data) {
			data = $.parseJSON(data);
			// 在提示框中显示返回消息
			$("#hintBody").text(data.msg);
			// 显示提示框
			$("#hintModal").modal("show");
			// 2秒后,自动隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 刷新页面
				if(data.code == 200) {
					window.location.reload();
				}
			}, 2000);
		}
	);
}