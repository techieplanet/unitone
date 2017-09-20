<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/modules/drilldown.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/modules/exporting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/code/modules/no-data-to-display.js"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_total_receivable')}">
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-blue">
                <div class="inner">
                  <h3><c:out value="${totalOutstanding}" /></h3>
                  <p class="bold">TOTAL RECEIVABLE</p>
                </div>
                <div class="icon">
                  <i class="fa fa-cart-plus"></i>
                </div>
                <!--<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>-->
                <a href="" onclick="return false;" class="small-box-footer">&nbsp;</i></a>
              </div>
            </div><!-- ./col -->
            </c:if>
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_total_due_receivable')}">
            <div class="col-lg-3 col-xs-6">
              <!-- small box -->
              <div class="small-box bg-green">
                <div class="inner">
                    <h3><c:out value="${totalDue}" /></h3>
                    <p class="bold">TOTAL DUE RECEIVABLE</p>
                </div>
                <div class="icon">
                  <i class="fa fa-cart-plus"></i>
                </div>
                <!--<a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>-->
                <a href="" onclick="return false;" class="small-box-footer">&nbsp;</i></a>
              </div>
            </div><!-- ./col -->
            </c:if>
            
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_total_stock_value')}">
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
            </c:if>
            
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_total_commission_payable')}">
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
            </c:if>
          </div><!-- /.row -->
          
          <div class="row">
              <c:if test="${fn:contains(sessionScope.user.permissions, 'view_income')}">
            <div class="col-md-3 col-sm-5 col-xs-12">
                <div class="callout bg-green">
                    <strong>
                        INCOME
                        <span class="pull-right">${income}</span>
                    </strong>
                </div>
            </div><!-- /.col -->
              </c:if>
            
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_annual_maintenance')}">
            <div class="col-md-3 col-sm-5 col-xs-12">
                <div class="callout bg-green">
                    <strong>
                        ANNUAL MAINTENANCE
                        <span class="pull-right">${annualMaintenance}</span>
                    </strong>
                </div>
            </div><!-- /.col -->
            </c:if>
            
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_with_holding_tax')}">
            <div class="col-md-3 col-sm-5 col-xs-12">
                <div class="callout bg-olive">
                    <strong>
                     WITHHOLDING TAX
                        <span class="pull-right">${withHoldingTax}</span>
                    </strong>
                </div>
            </div><!-- /.col -->
            </c:if>
            
            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_vat')}">
            <div class="col-md-3 col-sm-5 col-xs-12">
                <div class="callout bg-olive">
                    <strong>
                        V.A.T
                        <span class="pull-right">${VAT}</span>
                    </strong>
                </div>
            </div><!-- /.col -->
            </c:if>
        </div><!-- /.row -->
          
          <!--Perfomance Tabs-->
          <div class="box box-primary">
                <div class="box-header with-border header-stee-blue">
                  <h3 class="box-title box-title-with-bg-blue text-center">Sales Summary</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                    <div class="nav-tabs-custom">
                          <ul class="nav nav-tabs">
                            <li class="active"><a href="#ordersummary-pane" data-toggle="tab">Orders</a></li>
                            <li class=""><a href="#paymentsummary-pane" data-toggle="tab">Payments</a></li>
                            <li class=""><a href="#performance-pie-pane" data-toggle="tab">Percentages</a></li>
                            <li id="p-bar-li"><a href="#performance-bar-pane" data-toggle="tab">Numbers</a></li>
                          </ul>
                          <div class="tab-content">
                             <div class="active tab-pane" id="ordersummary-pane">
                                 <div class="row">
                                     <div class="col-md-1"></div> 
                                     <div class="col-md-10 bg-aqua padding10">
                                        <div class="row">
                                            <div class="col-md-2 col-md-offset-1">
                                                <label>Start Date: </label>
                                                <input type="text" name="start_date" id="start_date" class="datepicker  text-black" readonly="readonly">
                                            </div>

                                            <div class="col-md-2">
                                                <label>End Date: </label>
                                                <input type="text" name="end_date" id="end_date" class="datepicker  text-black" readonly="readonly">
                                            </div>

                                            <div class="col-md-2">
                                                <label>Group By:</label><br/>
                                                <select name="groupby" id="groupby" class=" text-black">
                                                    <option value="1">Day</option>
                                                    <option value="2">Month</option>
                                                    <option value="3">Year</option>
                                                </select>
                                            </div>

                                            <div class="col-md-1">
                                                <br/>
                                                <a class="btn btn-primary" href="#" onclick="filterOrderSummary()">Filter</a>
                                            </div>
                                        </div>
                                     </div>
                                     <div class="col-md-1"></div> 
                                 </div>
                                 
                                     <div class="row">
                                         <div id="order-combo" class="col-md-12">
                                         </div>
                                     </div>
                                 </div>
                            
                            
                                <div class="tab-pane" id="paymentsummary-pane">
                                    <div class="row">
                                        <div class="col-md-1"></div> 
                                        <div class="col-md-10 bg-aqua padding10">
                                           <div class="row">
                                               <div class="col-md-2 col-md-offset-1">
                                                   <label>Start Date: </label>
                                                   <input type="text" name="lstart_date" id="lstart_date" class="datepicker  text-black" readonly="readonly">
                                               </div>

                                               <div class="col-md-2">
                                                   <label>End Date: </label>
                                                   <input type="text" name="lend_date" id="lend_date" class="datepicker  text-black" readonly="readonly">
                                               </div>

                                               <div class="col-md-2">
                                                   <label>Group By:</label><br/>
                                                   <select name="lgroupby" id="lgroupby" class=" text-black">
                                                       <option value="1">Day</option>
                                                       <option value="2">Month</option>
                                                       <option value="3">Year</option>
                                                   </select>
                                               </div>

                                               <div class="col-md-1">
                                                   <br/>
                                                   <a class="btn btn-primary" href="#" onclick="filterLodgementSummary()">Filter</a>
                                               </div>
                                           </div>
                                        </div>
                                        <div class="col-md-1"></div> 
                                    </div>

                                        <div class="row">
                                            <div id="payment-combo" class="col-md-10">
                                            </div>
                                        </div>
                               </div><!-- /.tab-pane -->

                               <div class="tab-pane" id="performance-pie-pane">
                                   <div class="row">
                                       <div id="performance-pie" class="col-md-10"></div>
                                   </div>
                               </div><!-- /.tab-pane -->

                               <div class="tab-pane" id="performance-bar-pane">
                                   <div id="performance-bar" class="col-md-10"></div>
                               </div><!-- /.tab-pane -->

                          </div><!-- /.tab-content -->
                        </div><!-- /.nav-tabs-custom -->
                </div>
          </div>
          
          

    <!--COSTS LINE-->
        <div class="row" title="Number of Customers">
            <div class="col-md-3 col-sm-6 col-xs-12">
              <div class="info-box">
                <span class="info-box-icon bg-aqua"><i class="fa fa-users"></i></span>
                <div class="info-box-content">
                  <span class="info-box-text"><strong>Number of Customers</strong></span>
                  <span class="info-box-number">${customerCount}</span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            
            <div class="col-md-3 col-sm-6 col-xs-12" title="Number of Approved Agents">
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

            <div class="col-md-3 col-sm-6 col-xs-12" title="Processing / Completed Orders">
              <div class="info-box">
                <span class="info-box-icon bg-green"><i class="fa fa-cart-arrow-down"></i></span>
                <div class="info-box-content">
                    <span class="info-box-text"><strong>Processing / Completed <br/>Orders</strong></span>
                  <span class="info-box-number">${processingOrders} / ${completedOrders}</span>
                </div><!-- /.info-box-content -->
              </div><!-- /.info-box -->
            </div><!-- /.col -->
            <div class="col-md-3 col-sm-6 col-xs-12" title="Average Customer per Agent">
              <div class="info-box">
                <span class="info-box-icon bg-yellow"><i class="fa fa-user-times"></i></span>
                <div class="info-box-content">
                    <span class="info-box-text"><strong>Average Customer <br/>per Agent</strong></span>
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
        $( ".datepicker" ).datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd-mm-yy",
            showAnim: "drop"
          });
        
      //set high charts global color scheme for all high charts instances on this page
        Highcharts.setOptions({
                colors: ['#3366CC', '#DC3912', '#FF9900', '#00a65a', '#109618', '#990099', '#0099C6', '#DD4477', '#AAAA11', '#B77322']
            });
            
      
      drawOrderSummaryChart(${orderSummary});
      drawLodgementSummaryChart(${lodgementSummary});
      drawPerformancePieChart();
      drawPerformanceBarChart();
      
    });
    
    function filterOrderSummary(){
        formData = {startDate: $('#start_date').val(), endDate: $('#end_date').val(), grouping: $('#groupby').val()};
        console.log('formData: ' + JSON.stringify(formData));
        genericAjax('${pageContext.request.contextPath}', 'Dashboard?action=ordersummary', 'GET', formData, drawOrderSummaryChart);
        return false;
    }
    
    
    function drawOrderSummaryChart(orderSummaryJSON){
        Highcharts.setOptions({
                colors: ['#3366CC', '#DC3912', '#FF9900', '#00a65a', '#109618', '#990099', '#0099C6', '#DD4477', '#AAAA11', '#B77322']
            });
            
        console.log("inside drawOrderSummaryChart");
        var seriesArray = new Array();
        var countsObject = {name: 'Orders Count', type: 'column', data: new Array()};
        var valuesObject = {name: 'Orders Value', type: 'spline', yAxis: 1, color: '#00a65a', data: new Array()};
        var categoriesArray = new Array();
        
        console.log("orderSummaryJSON: " + JSON.stringify(orderSummaryJSON))
        
        
        //if( (typeof orderSummaryList) == 'object') {
        if( (typeof orderSummaryJSON) != 'object') {
            //This is used when we are calling this function as an AJAx callback function. The value returned is a JSON string. 
            //Needs to be parsed into JS object
            console.log("inside 1");
            orderSummaryList = JSON.parse(orderSummaryJSON);    
        }
        else{
            //This is used when we are calling this function directly on page load. The value returned is a JSON object. 
            //No need to parse into JS object
            console.log("inside 2");
            orderSummaryList = orderSummaryJSON;
        }
            
        console.log("orderSummaryList.length " + orderSummaryList[0]);
        
        for(var i=0; i < orderSummaryList.length; i++){
                console.log("loop");
                var summary = orderSummaryList[i];
                countsObject.data.push(summary.count); 
                valuesObject.data.push(summary.value);
                categoriesArray.push(summary.date)
        }
            
            seriesArray.push(countsObject);
            seriesArray.push(valuesObject);
            
            
            
            
            
        $('#order-combo').highcharts({
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: 'Order Summary'
            },
            subtitle: {
                text: ''
            },
            xAxis: [{
                categories: categoriesArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                    
                    labels: {
                        //format: '{value}°C',
                        style: {
                            //color: Highcharts.getOptions().colors[1]
                            fontWeight: 'bold'
                        }
                    },
                    title: {
                        text: 'Number of Orders',
                        style: {
                          //  color: Highcharts.getOptions().colors[1]
                          //fontWeight: 'bold'
                        }
                    }
                },{ // Secondary yAxis
                        title: {
                            text: 'Value of Orders (NGN)',
                            style: {
                                color: Highcharts.getOptions().colors[3]
                                //fontWeight: 'bold'
                            }
                        },
                        labels: {
                            formatter: function(){
                                return thousandSeparator(this.value);
                            },
                            style: {
                                color: Highcharts.getOptions().colors[3],
                                fontWeight: 'bold'
                            }
                        },
                        opposite: true

             }],
            tooltip: {
                shared: true
            },
            lang: {noData: 'No data to display' },
            legend: {
                layout: 'horizontal',
                align: 'center',
                //x: 120,
                //verticalAlign: 'top',
                //y: 100,
                //floating: true,
                //backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            series: seriesArray
        });
    }
    
    
    function filterLodgementSummary(){
        console.log('filterLodgementSummary');
        formData = {startDate: $('#lstart_date').val(), endDate: $('#lend_date').val(), grouping: $('#lgroupby').val()};
        console.log('formData: ' + JSON.stringify(formData));
        genericAjax('${pageContext.request.contextPath}', 'Dashboard?action=lodgementsummary', 'GET', formData, drawLodgementSummaryChart);
        return false;
    }
    
    function drawLodgementSummaryChart(lodgementSummaryJSON){
        Highcharts.setOptions({
                colors: ['#3366CC', '#DC3912', '#FF9900', '#00a65a', '#109618', '#990099', '#0099C6', '#DD4477', '#AAAA11', '#B77322']
            });
            
        console.log("inside drawLodgementSummaryChart");
        var seriesArray = new Array();
        var countsObject = {name: 'Payments Count', type: 'column', data: new Array()};
        var valuesObject = {name: 'Payments Value', type: 'spline', yAxis: 1, color: '#00a65a', data: new Array()};
        <c:if test="${sessionScope.availablePlugins.loyalty != null}">
            var loyaltyObject = {name: 'Loyalty Value', type: 'column', data: new Array()};
        </c:if>
            
        var categoriesArray = new Array();
        
        console.log("lodgementSummaryJSON " + JSON.stringify(lodgementSummaryJSON))
        
        var lodgementSummaryList = '';
        
        if( (typeof lodgementSummaryJSON) != 'object') {
            //This is used when we are calling this function as an AJAx callback function. The value returned is a JSON string. 
            //Needs to be parsed into JS object
            console.log("inside 1");
            lodgementSummaryList = JSON.parse(lodgementSummaryJSON);    
        }
        else{
            //This is used when we are calling this function directly on page load. The value returned is a JSON object. 
            //No need to parse into JS object
            console.log("inside 2");
            lodgementSummaryList = lodgementSummaryJSON;
        }
            
        console.log("lodgementSummaryList.length " + lodgementSummaryList[0]);
        
        for(var i=0; i < lodgementSummaryList.length; i++){
                console.log("loop");
                var summary = lodgementSummaryList[i];
                countsObject.data.push(summary.count); 
                
                <c:if test="${sessionScope.availablePlugins.loyalty != null}">
                    loyaltyObject.data.push(summary.reward_value); 
                </c:if>
                    
                valuesObject.data.push(summary.value);
                categoriesArray.push(summary.date)
        }
            
            seriesArray.push(countsObject);
            <c:if test="${sessionScope.availablePlugins.loyalty != null}">
                seriesArray.push(loyaltyObject);
            </c:if>
            seriesArray.push(valuesObject);
            
            
            
            
            
        $('#payment-combo').highcharts({
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: 'Payments Summary'
            },
            subtitle: {
                text: ''
            },
            xAxis: [{
                categories: categoriesArray,
                crosshair: true
            }],
            yAxis: [{ // Primary yAxis
                    
                    labels: {
                        //format: '{value}°C',
                        style: {
                            //color: Highcharts.getOptions().colors[1]
                            fontWeight: 'bold'
                        }
                    },
                    title: {
                        text: 'Number of Payments',
                        style: {
                          //  color: Highcharts.getOptions().colors[1]
                          //fontWeight: 'bold'
                        }
                    }
                },{ // Secondary yAxis
                        title: {
                            text: 'Value of Payments (NGN)',
                            style: {
                                color: Highcharts.getOptions().colors[3]
                                //fontWeight: 'bold'
                            }
                        },
                        labels: {
                            formatter: function(){
                                return thousandSeparator(this.value);
                            },
                            style: {
                                color: Highcharts.getOptions().colors[3],
                                fontWeight: 'bold'
                            }
                        },
                        opposite: true

             }],
            tooltip: {
                shared: true
            },
            lang: {noData: 'No data to display' },
            legend: {
                layout: 'horizontal',
                align: 'center',
                //x: 120,
                //verticalAlign: 'top',
                //y: 100,
                //floating: true,
                //backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            series: seriesArray
        });
    }
    
    function drawPerformancePieChart(){
        var projectDataArray = new Array();
        var drillObjectsArray = new Array();
        
        <c:forEach items="${projectPerformanceBySalesQuota}" var="project">
                projectObject = {};
                projectObject.name = "${project.projectName}";
                projectObject.y = ${project.valuePercentage};
                projectObject.drilldown = "${project.projectName}";
                projectDataArray.push(projectObject);
                
                //handle the units
                var drillObject = {};
                drillObject.name = "${project.projectName}";
                drillObject.id = "${project.projectName}";
                drillObjectDataArray = new Array();
                <c:forEach items="${project.units}" var="unit">
                    drillObjectDataArray.push(new Array("${unit.unitName}",${unit.valuePercentage})); //push a unit into the drill down object
                </c:forEach>
                    
                drillObject.data = drillObjectDataArray;
                drillObjectsArray.push(drillObject);
                
        </c:forEach>
        
        $('#performance-pie').highcharts({
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
                series: {
                    dataLabels: {
                        enabled: true,
                        format: '{point.name}: {point.y:.1f}%'
                    }
                }
            },
            tooltip: {
                //headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                //pointFormat: '<span>{point.name}</span>: <b>{point.y:.1f}%</b> of total<br/>'
//                pointFormat: '<span>{point.name}</span>: <b>{point.y:.1f}%</b> of total<br/>'
                useHtml: true,
                formatter: function (){
                    return this.series.name + '<br/><b>'+ this.key +'</b>: '  + this.y.toFixed(1);
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
            },
            lang: {noData: 'No data to display' }
        });
    }
    
    
    function drawPerformanceBarChart(){
        Highcharts.setOptions({
                colors: ['#CC0000', '#00a65a', '#3366CC', '#FF9900', '#109618', '#990099', '#0099C6', '#DD4477', '#AAAA11', '#B77322']
            });
            
            
        var projectDataArray = new Array();
        var criteriaArray = new Array("Stock", "Sold"); //Mind the order
        var seriesArray = new Array();
        
        
        //stock: unsold
        var seriesObject = {};
        seriesObject.name = "In Stock";
        <c:forEach items="${projectPerformanceByStockSold}" var="project">
                projectObject = {};
                projectObject.name = "${project.projectName}";
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
        <c:forEach items="${projectPerformanceByStockSold}" var="project">
                projectObject = {};
                projectObject.name = "${project.projectName}";
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
        lang: {noData: 'No data to display' },
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
        <c:forEach items="${projectPerformanceByStockSold}" var="project">
                projectObject = {};
                projectUnitsArray = new Array();
                projectObject.name = "${project.projectName}";
                
                //handle the units
                <c:forEach items="${project.units}" var="unit">
                    projectUnitsArray.push(new Array("${unit.name}",(${unit.setupStock}-${unit.sold})));
                </c:forEach>
                projectObject.data = projectUnitsArray;
                
                drillDownSeriiObject1["${project.projectName}"] = projectObject;
        </c:forEach>
            console.log("drillDownSeriiObject1: " + JSON.stringify(drillDownSeriiObject1));
        //sold
        <c:forEach items="${projectPerformanceByStockSold}" var="project">
                projectObject = {};
                projectUnitsArray = new Array();
                projectObject.name = "${project.projectName}";
                
                //handle the units
                <c:forEach items="${project.units}" var="unit">
                    projectUnitsArray.push(new Array("${unit.name}", ${unit.sold}));
                </c:forEach>
                projectObject.data = projectUnitsArray;
                
                drillDownSeriiObject2["${project.projectName}"] = projectObject;
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

