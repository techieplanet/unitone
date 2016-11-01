<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/modules/drilldown.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/modules/exporting.js"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
            
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-green">
                <div class="inner">
                    <h3><c:out value="${totalDue}" /></h3>
                    <p class="bold">TOTAL DUE PAYMENTS</p>
                </div>
                <div class="icon">
                  <i class="fa fa-cart-plus"></i>
                </div>
                <!--<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>-->
                <a href="" onclick="return false;" class="small-box-footer">&nbsp;</i></a>
              </div>
            </div><!-- ./col -->
            
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-blue">
                <div class="inner">
                  <h3><c:out value="${totalOutstanding}" /></h3>
                  <p class="bold">TOTAL OUTSTANDING PAYMENTS</p>
                </div>
                <div class="icon">
                  <i class="fa fa-cart-plus"></i>
                </div>
                <!--<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>-->
                <a href="" onclick="return false;" class="small-box-footer">&nbsp;</i></a>
              </div>
            </div><!-- ./col -->
            
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-yellow">
                <div class="inner">
                    <h3><c:out value="${commissionsPayable}" /></h3>
                  <p class="bold">TOTAL COMMISSIONS PAYABLE</p>
                </div>
                <div class="icon">
                  <i class="fa fa-location-arrow"></i>
                </div>
                <!--<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>-->
                <a href="" onclick="return false;" class="small-box-footer">&nbsp;</i></a>
              </div>
            </div><!-- ./col -->
          
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-red">
                <div class="inner">
                    <h3><c:out value="${totalStockValue}" /></h3>
                  <p class="bold">TOTAL STOCK VALUE</p>
                </div>
                <div class="icon">
                  <i class="ion ion-pie-graph"></i>
                </div>
                <!--<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>-->
                <a href="" onclick="return false;" class="small-box-footer">&nbsp;</i></a>
              </div>
            </div><!-- ./col -->
            
          </div><!-- /.row -->
          
          
          <!--Perfomance Tabs-->
          <div class="box box-primary">
                <div class="box-header with-border header-stee-blue">
                  <h3 class="box-title box-title-with-bg-blue text-center">Sales Summary</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                    <div class="nav-tabs-custom">
                          <ul class="nav nav-tabs">
                            <li class="active"><a href="#performance-pie-pane" data-toggle="tab">Percentages</a></li>
                            <li id="p-bar-li"><a href="#performance-bar-pane" data-toggle="tab">Numbers</a></li>
                          </ul>
                          <div class="tab-content">
                            <div class="active tab-pane" id="performance-pie-pane">
                                <div id="performance"></div>
                            </div><!-- /.tab-pane -->

                            <div class="tab-pane" id="performance-bar-pane">
                                <div id="performance-bar" style="width:80%;"></div>
                            </div><!-- /.tab-pane -->



                          </div><!-- /.tab-content -->
                        </div><!-- /.nav-tabs-custom -->
                </div>
          </div>
          
          

    <!--COSTS LINE-->
        <div class="row">
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
                <span class="info-box-icon bg-aqua"><i class="fa fa-users"></i></span>
                <div class="info-box-content">
                  <span class="info-box-text"><strong>Number of Customers</strong></span>
                  <span class="info-box-number">${customerCount}</span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
                <span class="info-box-icon bg-red"><i class="fa fa-user-plus"></i></span>
                <div class="info-box-content">
                  <span class="info-box-text"><strong>Number of Approved Agents</strong></span>
                  <span class="info-box-number">${agentCount}</span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->

            <!-- fix for small devices only -->
            <div class="clearfix visible-sm-block"></div>

            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
                <span class="info-box-icon bg-green"><i class="fa fa-cart-arrow-down"></i></span>
                <div class="info-box-content">
                    <span class="info-box-text"><strong>Processing / Completed Orders</strong></span>
                  <span class="info-box-number">${processingOrders} / ${completedOrders}</span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
                <span class="info-box-icon bg-yellow"><i class="fa fa-user-times"></i></span>
                <div class="info-box-content">
                    <span class="info-box-text"><strong>Average Customer per Agent</strong></span>
                  <span class="info-box-number">${customersPerAgent}</span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
    </div><!-- /.row -->
    
<!--    <div class="row">
        <div class="col-md-6">
            <div class="box box-primary">
                
                <div class="box-header">
                  <h3 class="box-title block">
                      Agent List
                  </h3>
                </div> /.box-header 

                 <div class="box-body">   
                     table goes here
                </div> /.box-body                 
            </div> /.box 
        </div>
   
        <div class="col-md-6">
            <div class="box box-primary">
                
                <div class="box-header">
                  <h3 class="box-title block">
                      Agent List
                  </h3>
                </div> /.box-header 

                 <div class="box-body">   
                     table goes here
                </div> /.box-body                 
            </div> /.box 
        </div>
    </div>-->
    
<script>
    
    $(function (){
      //set high charts global color scheme for all high charts instances on this page
        Highcharts.setOptions({
                colors: ['#3366CC', '#DC3912', '#FF9900', '#00a65a', '#109618', '#990099', '#0099C6', '#DD4477', '#AAAA11', '#B77322']
            });
            
            
      drawPerformancePieChart();
      drawPerformanceBarChart();
      
    });
    
    
    
    function drawPerformancePieChart(){
        var projectDataArray = new Array();
        var drillObjectsArray = new Array();
        
        <c:forEach items="${projectPerformance}" var="project">
                projectObject = {};
                projectObject.name = '${project.projectName}';
                projectObject.y = ${project.projectPercentage};
                projectObject.drilldown = '${project.projectName}';
                projectDataArray.push(projectObject);
                
                //handle the units
                var drillObject = {};
                drillObject.name = '${project.projectName}';
                drillObject.id = '${project.projectName}';
                drillObjectDataArray = new Array();
                <c:forEach items="${project.units}" var="unit">
                    drillObjectDataArray.push(new Array('${unit.name}',${unit.percentage})); //push a unit into the drill down object
                </c:forEach>
                    
                drillObject.data = drillObjectDataArray;
                drillObjectsArray.push(drillObject);
                
        </c:forEach>
        
        $('#performance').highcharts({
            chart: {
                type: 'pie',
                events: {
                        load:function(){
                            $("text:contains(Highcharts.com)").css("display","none");
                        }
                    }
            },
            title: {
                text: 'Projects and their units'
            },
            subtitle: {
                text: 'Click the slices to view units under each project.'
            },
            plotOptions: {

            },
            tooltip: {
                //headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                //pointFormat: '<span>{point.name}</span>: <b>{point.y:.1f}%</b> of total<br/>',
                useHtml: true,
                formatter: function (){
                    return '<b>'+ this.key +'</b><br>' + 
                           '<b>' + this.series.name + ': </b>' + this.y;
                }

            },
            series: [{
                name: 'Projects',
                data: projectDataArray
            }],
            drilldown: {
                activeDataLabelStyle: { 
                    "cursor": "pointer", 
                    "color": "#000000", 
                    "fontWeight": "normal", 
                    "fontSize": "12px",
                    "textDecoration": "none", 
                },
                series: drillObjectsArray
            }
        });
    }
    
    
    function drawPerformanceBarChart(){
        Highcharts.setOptions({
                colors: ['#CC0000', '#00a65a', '#3366CC', '#FF9900', '#109618', '#990099', '#0099C6', '#DD4477', '#AAAA11', '#B77322']
            });
            
        var projectDataArray = new Array();
        var criteriaArray = new Array("Stock", "Sold"); //Mind the order
        var seriesArray = new Array();
        
//        {
//            name: 'Things',
//            color: '#3150b4',
//            data: [{
//                name: 'Animals',
//                y: 5,
//                drilldown: true
//            }, {
//                name: 'Fruits',
//                y: 5,
//                drilldown: true
//            }]
//        }
        
        //stock: unsold
        var seriesObject = {};
        seriesObject.name = "In Stock";
        <c:forEach items="${projectPerformance}" var="project">
                projectObject = {};
                projectObject.name = '${project.projectName}';
                projectObject.y = (${project.setupStock} - ${project.sold});
                projectObject.drilldown = true;
                projectDataArray.push(projectObject);
        </c:forEach>
        seriesObject.data = projectDataArray;
        seriesArray.push(seriesObject);
            
        //sold
        projectDataArray = new Array();
        seriesObject = {};
        seriesObject.name = "Sold";
        <c:forEach items="${projectPerformance}" var="project">
                projectObject = {};
                projectObject.name = '${project.projectName}';
                projectObject.y = ${project.sold};
                projectObject.drilldown = true;
                projectDataArray.push(projectObject);
        </c:forEach>
        seriesObject.data = projectDataArray;
        seriesArray.push(seriesObject);
            
            
        $('#performance-bar').highcharts({
            chart: {
            type: 'column',
            events: {
                load:function(){
                    $("text:contains(Highcharts.com)").css("display","none");
                },
                drilldown: function (e) {
                    if (!e.seriesOptions) {
                      var values = getChartAndDrillDownArray(this,e);   
                      var chart = values[0];
                      var series = values[1];
                      var series2 = values[2];
                      
                      chart.addSingleSeriesAsDrilldown(e.point, series); 
                      chart.addSingleSeriesAsDrilldown(e.point, series2);
                      chart.applyDrilldown();
                    }
                },
                load: function(event) {
                    event.target.reflow();
                  }
            }
        },
        title: {
            text: 'Projects and their units'
        },
        subtitle: {
                text: 'Click the bars to view units under each project.'
            },
        xAxis: {
            type: 'category'
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            column: {stacking: 'normal'},
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        style: { textShadow: false}
                    }
                }
        },
        legend: {
            layout: 'horizontal',
            align: 'bottom',
            align: 'center',
            margin: 18,
            //floating: true,
            borderWidth: 1,
            borderRadius: 5,
            //shadow: true
        },
        credit: false,
        series: seriesArray,
        drilldown: {
            activeAxisLabelStyle: { 
                "cursor": "pointer", 
                "color": "#000000", 
                "fontWeight": "normal", 
                "fontSize": "12px",
                "textDecoration": "none" 
            },
            activeDataLabelStyle: { 
                    "cursor": "pointer", 
                    "color": "#ffffff", 
                    "fontWeight": "bold", 
                    "fontSize": "12px",
                    "overflow": "justify",
                    "textDecoration": "none", 
                },
            series: []
        }
        
        });

    }
    
    function getChartAndDrillDownArray(obj,e){
        var drillDownSeriiObject1 = {color: 'green'};
        var drillDownSeriiObject2 = {color: '#FF0000'};
        
//        drilldowns = {
//            'Animals': {
//                name: 'Animals',
//                color: '#3150b4',
//                data: [
//                    ['Cows', 2],
//                    ['Sheep', 3]
//                ]
//            },

        //stock: unsold
        <c:forEach items="${projectPerformance}" var="project">
                projectObject = {};
                projectUnitsArray = new Array();
                projectObject.name = '${project.projectName}';
                
                //handle the units
                <c:forEach items="${project.units}" var="unit">
                    projectUnitsArray.push(new Array('${unit.name}',(${unit.setupStock}-${unit.sold})));
                </c:forEach>
                projectObject.data = projectUnitsArray;
                
                drillDownSeriiObject1['${project.projectName}'] = projectObject;
        </c:forEach>
            console.log("drillDownSeriiObject1: " + JSON.stringify(drillDownSeriiObject1));
        //sold
        <c:forEach items="${projectPerformance}" var="project">
                projectObject = {};
                projectUnitsArray = new Array();
                projectObject.name = '${project.projectName}';
                
                //handle the units
                <c:forEach items="${project.units}" var="unit">
                    projectUnitsArray.push(new Array('${unit.name}', ${unit.sold}));
                </c:forEach>
                projectObject.data = projectUnitsArray;
                
                drillDownSeriiObject2['${project.projectName}'] = projectObject;
        </c:forEach>
            
            
        var myChart = obj,
                            drilldowns1 = drillDownSeriiObject1,
                            drilldowns2 = drillDownSeriiObject2,
                            series = drilldowns1[e.point.name],
                            series2 = drilldowns2[e.point.name];
                    
                    var arr = new Array(myChart,series, series2);
                    return arr;
    }
    
</script>

