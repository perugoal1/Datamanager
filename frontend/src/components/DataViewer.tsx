import React, { useState, useRef } from 'react'
import { AgGridReact } from 'ag-grid-react';
import 'ag-grid-enterprise';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';

import apiService from '../services/apiService';

const DataViewer = () => {
  const [gridApi, setGridApi] = useState(null);
  const gridRef = useRef<any>(null);

  const columns = [
    { headerName: "InvoiceNo", field: "invoiceNo", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
    { headerName: "stockCode", field: "stockCode", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']} },
    { headerName: "description", field: "description", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
    { headerName: "quantity", field: "quantity", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
    { headerName: "invoiceDate", field: "invoiceDate", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
    { headerName: "unitPrice", field: "unitPrice", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
    { headerName: "customerId", field: "customerId", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
    { headerName: "country", field: "country", filter: "agTextColumnFilter", filterParams:{ filterOptions: ['contains']}},
  ]

  const datasource = {
    getRows(params) {
      console.log(JSON.stringify(params.request, null, 1));
      const { startRow, filterModel, sortModel } = params.request
      let url = `?`
      //Sorting
      if (sortModel.length) {
        const { colId, sort } = sortModel[0]
        url += `sort=${colId}&order=${sort}&`
      }
      //Filtering
      const filterKeys = Object.keys(filterModel)
      filterKeys.forEach(filter => {
        url += `${filter}=${filterModel[filter].filter}&`
      })
      //Pagination
      url += `page=${startRow/10}`
      console.log(123123, url);
      apiService.getData(url).then((response) => {
        params.successCallback(response.data.content, response.data.totalElements);
      }).catch(error => {
        console.error(error);
        params.failCallback();
      });
   
      //gridRef.current.api.sizeColumnsToFit();
    }
  };
  
  const onGridReady = (params) => {
    setGridApi(params);
    params.api.setServerSideDatasource(datasource);
  }

  const refresh = () =>{
    gridRef.current.api.refreshServerSideStore();
  }

  return (
    <div>
      <h3>View Data</h3>
      <div className="refresh"><button  className="btn btn-secondary" onClick={refresh}>Refresh <span>&#8635;</span></button></div>
      <div className="ag-theme-alpine">
        <AgGridReact
          columnDefs={columns}
          pagination={true}
          paginationPageSize={10}
          cacheBlockSize={10}
          domLayout="autoHeight"
          rowModelType={'serverSide'}
          serverSideStoreType={'partial'}
          onGridReady={onGridReady}
          defaultColDef={{ filter: true, floatingFilter: true, sortable: true }}
          ref={gridRef}
        />
      </div>
    </div>
  );
};
export default DataViewer