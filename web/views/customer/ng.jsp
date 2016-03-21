<%!
    //String root = request.getContextPath();
%>    
<!DOCTYPE html>
<html ng-app="demoApp" lang="en">
<head>
    <title id='Description'>An end-user can easily search through a Grid's data via the built-in Search Input field. The Grid automatically filters records and displays only those that match the user's search string.
    </title>
    <meta name="description" content="An end-user can easily search through a Grid's data via the built-in Search Input field. When searching via the Search Input Field, the JavaScript Grid automatically filters records and displays only those that match the user's search string">     
    <link rel="stylesheet" href="../../ngwidgets/styles/ngx.base.css" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngxcore.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngxdata.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngxbuttons.js"></script>
    <script type="text/javascript" src="../../ngwidgets/ngxscrollbar.js"></script>
    <script type="text/javascript" src="../../ngwidgets/ngxgridview.js"></script>
    <script type="text/javascript" src="../../ngwidgets/ngxcheckbox.js"></script>
    <script type="text/javascript" src="../../ngwidgets/ngxlistbox.js"></script>
    <script type="text/javascript" src="../../ngwidgets/ngxdropdownlist.js"></script>
    <script type="text/javascript" src="../../scripts/demos.js"></script>
    <script type="text/javascript">
        var demoApp = angular.module("demoApp", ["ngwidgets"]);
        demoApp.controller("demoController", function ($scope) {            
            // create ngxGridView.
            $scope.gridSettings =
            {
                source:  {
                    datatype: "xml",
                    dataFields: [
                         { name: 'SupplierName', type: 'string' },
                         { name: 'Quantity', type: 'number' },
                         { name: 'OrderDate', type: 'date' },
                         { name: 'OrderAddress', type: 'string' },
                         { name: 'Freight', type: 'number' },
                         { name: 'Price', type: 'number' },
                         { name: 'City', type: 'string' },
                         { name: 'ProductName', type: 'string' },
                         { name: 'Address', type: 'string' }
                    ], 
                    url: '../sampledata/orderdetailsextended.xml',
                    root: 'DATA',
                    record: 'ROW'
                },
                pageable: true,
                pagerButtonsCount: 10,
                altRows: true,
                filterable: true,
                height: 400,
                filterMode: 'simple',
                width: 850,
                columns: [
                  { text: 'Supplier Name', cellsAlign: 'center', align: 'center', dataField: 'SupplierName', width: 250 },
                  { text: 'Name', columngroup: 'ProductDetails', cellsAlign: 'center', align: 'center', dataField: 'ProductName', width: 250 },
                  { text: 'Quantity', columngroup: 'ProductDetails', dataField: 'Quantity', cellsformat: 'd', cellsAlign: 'center', align: 'center', width: 80 },
                  { text: 'Price', columngroup: 'ProductDetails', dataField: 'Price', cellsformat: 'c2', align: 'center', cellsAlign: 'center', width: 70 },
                  { text: 'Address', columngroup: 'Location', cellsAlign: 'center', align: 'center', dataField: 'Address', width: 120 },
                  { text: 'City', columngroup: 'Location', cellsAlign: 'center', align: 'center', dataField: 'City' }
                ]
            };
        });
    </script>
</head>
 <body ng-controller="demoController">
    <ngx-grid-view ngx-settings="gridSettings"></ngx-grid-view>
    <% 
        out.println(request.getContextPath());
    %>
</body>
</html>
