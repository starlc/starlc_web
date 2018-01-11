var classConstants = {
		SELFCONTAINER_START_X: 30,
		SELFCONTAINER_START_Y: 10,
		
		SELFCONTAINER_WIDTH: 0,
		SELFCONTAINER_HEIGHT: 0,
		
		SELFCONTAINER_LINE_MAX_WIDTH: 0,
		SELFCONTAINER_LINE_MAX_HEIGHT: 0,
		SELFCONTAINER_LINE_UNIT_NUM: 3,
				
		INSIDE_UNIT_START_X: 30,
		INSIDE_UNIT_START_Y: 30,
		
		INSIDE_UNIT_LINE_START_X: 30,
		INSIDE_UNIT_LINE_START_Y: 30,
				
		INSIDE_UNIT_MAX_WIDTH: 0,
		INSIDE_UNIT_MAX_HEIGHT: 0,
		
		INSIDE_NEXT_X: 30,
		INSIDE_NEXT_Y: 30,
		
		ENTITY_WIDTH:260,
		ENTITY_HEIGHT:30,
		
		PAGE_WIDTH:100,
		PAGE_HEIGHT:90,
		PAGE_NUM:3,	
		PAGE_HSPACE:30,
		PAGE_VSPACE:10,
		
		HAVE_DATABASE:false,
		DATABASE_WIDTH:200,
		DATABASE_HEIGHT:60,
		
		HAVE_PROCESS:false,
		PROCESS_WIDTH:140,
		PROCESS_HEIGHT:60,
		
		FROMCONTAINER_MAX_WIDTH: 0,
		FROMCONTAINER_WIDTH: 200,
		FROMCONTAINER_HEIGHT: 200,
		FROMCONTAINER_HSPACE: 30,
		FROMCONTAINER_VSPACE: 30,
		FROM_NEXT_X: 30,
		FROM_NEXT_Y: 30,
		
		TOCONTAINER_MAX_WIDTH: 0,
		TOCONTAINER_WIDTH: 200,
		TOCONTAINER_HEIGHT: 200,
		TOCONTAINER_HSPACE: 30,
		TOCONTAINER_VSPACE: 30,
		TO_NEXT_X: 30,
		TO_NEXT_Y: 30
};

//获取展示数据
function getDisplayData(graph,parent) {
	//注册样式
	createPageStyle(graph);
	//创建layout 即分层便于控制

	//创建模块自身容器

	//
	var v1 = graph.insertVertex(parent, null, '图片测试', 30, 10, 460, 615,
		'swimlane;gradientColor=#d2d3d3;fillColor=#FFFFFF;');
	v1.geometry.alternateBounds = new mxRectangle(0, 0, 110, 70);
	
	//创建模块内部各种单元格
	//创建页面单元格
	createMxCell4Page(graph,parent,v1);
	//var v11 = graph.insertVertex(v1, null, 'Hello,', 10, 40, 120, 80);
	// var val = '<table style="width:100;height:90;overflow:hidden;WORD-WRAP:break-word;word-break:break-all;" >'+
	// '<tr style="width:100;height:90;overflow:hidden;WORD-WRAP:break-word;word-break:break-all;">'+
	// '<td style="font-size:12;padding-top:20px;text-align:center;height:90;overflow:hidden;WORD-WRAP:break-word;word-break:break-all;">'+
	// 'bbbb编辑页面<br/>BbbbEditPageForWorkFlow.jsp</td></tr></table>';
	//var pageStyle = 'shape=note;strokeColor=#34a7db;strokeWidth=0;verticalAlign=middle;overflow=hidden;fillColor=#FFFFFF;gradientColor=#93DFFE;html=1;pageDiagram=1';
	//var strV1 = '<table style="width:100;WORD-WRAP:break-word;word-break:break-all;"><tr><td style="font-size:12;text-align:center;width:100;">bbbb编辑页面<br/>BbbbEditPageForWorkFlow.jsp</td></tr></table>';
	// var v11 = graph.insertVertex(v1, "BbbbEditPageForWorkFlow", getHtml4Cell('bbbb编辑页面','BbbbEditPageForWorkFlow.jsp'), 10, 40, 120, 80,style1);
	// var v12 = graph.insertVertex(v1, "BbbbDoneListPage", getHtml4Cell('bbbb已办页面','BbbbDoneListPage.jsp'), 150, 40, 120, 80,style1);
	// //创建连线
	// var e1 = graph.insertEdge(parent, null, '', v11, v12);
	// var arrV = [];
	// for (var i = 0; i < 10; i++) {
	// 	var rowIndex = Math.floor(i/3),colIndex=i%3;
	// 	var xpoint = 10+140*colIndex;
	// 	var ypoint = 40+90*rowIndex;
	// 	arrV[i] = graph.insertVertex(v1, "id"+i, 'name'+i,xpoint , ypoint, 120, 80,pageStyle);
	// 	//console.log("xpoint: "+xpoint+",ypoint: "+ypoint);
	// }

	// for (var i = 0; i < 10; i++) {
	// 	graph.insertEdge(parent, null, '', arrV[getRandomNum()], arrV[getRandomNum()]);
	// }
	//创建依赖容器
	//创建被依赖容器

}

//创建页面Cell和连线
function createMxCell4Page(graph,parent,container){
	//var pageStyle = 'shape=note;strokeColor=#34a7db;strokeWidth=0;verticalAlign=middle;overflow=hidden;fillColor=#FFFFFF;gradientColor=#93DFFE;html=1;pageDiagram=1';
	var arrV = [];
	for (var i = 0; i < 10; i++) {
		var rowIndex = Math.floor(i/3),colIndex=i%3;
		var xpoint = 10+140*colIndex;
		var ypoint = 40+90*rowIndex;
		arrV[i] = graph.insertVertex(container, "id"+i, 'name'+i,xpoint , ypoint, 120, 80,'PAGE');
		//console.log("xpoint: "+xpoint+",ypoint: "+ypoint);
		//graph.createEdge(parent, null, 'rel', arrV[getRandomNum()], arrV[getRandomNum()]); //必须配合addEdge()一起使用;
	}

	var testEdge ;
	//创建连线以及锚点
	for (var i = 0; i < 10; i++) {
		testEdge = graph.insertEdge(parent, null, 'rel'+i, arrV[getRandomNum()], arrV[getRandomNum()]);
		createPoint4Edge(testEdge,i);
	}
}

function getHtml4Cell(cname,engName){
	var str ='<table style="width:100;WORD-WRAP:break-word;word-break:break-all;">'+
	'<tr><td style="font-size:12;text-align:center;width:100;">'+cname+'<br/>'+engName+'</td></tr></table>';
	return str;
}

function getRandomNum(){
	return Math.floor(Math.random()*10);
}


//创建页面样式并注册
//'shape=note;strokeColor=#34a7db;strokeWidth=0;verticalAlign=middle;overflow=hidden;fillColor=#FFFFFF;gradientColor=#93DFFE;html=1;pageDiagram=1'
function createPageStyle(graph){
	var style = new Object();
	style[mxConstants.STYLE_SHAPE] = 'note';
	style[mxConstants.STYLE_STROKECOLOR] = '#34a7db';
	style[mxConstants.STYLE_STROKEWIDTH] = 0;
	style[mxConstants.STYLE_VERTICAL_ALIGN] = 'middle';
	style[mxConstants.STYLE_OVERFLOW] ='hidden';
	style[mxConstants.STYLE_FILLCOLOR] = '#FFFFFF';
	style[mxConstants.STYLE_GRADIENTCOLOR] = '#93DFFE';

	style['html'] = 1;
	style['pageDiagram'] = 1;
	graph.getStylesheet().putCellStyle('PAGE',style);
}

//创建连接线的控制点
function createPoint4Edge(mxCell,i){
	var geometry = mxCell.getGeometry();
	// geometry.setTerminalPoint(new mxPoint(x1, y1), true);
	// geometry.points = [new mxPoint(x2, y2)];
	// geometry.setTerminalPoint(new mxPoint(x3, y3), false);

	geometry.setTerminalPoint(new mxPoint(0.25, 1), true);
	geometry.points = [new mxPoint(i*40, i*10+1)];
	geometry.setTerminalPoint(new mxPoint(0.5, 0), false);
}