<template>
  <div class="nodes">
    <ag-grid-vue
      style="width: 100%; height: 700px"
      class="ag-theme-alpine"
      rowSelection="multiple"
      :columnDefs="columnDefs"
      :rowData="rowData"
      :defaultColDef="defaultColDef"
      :gridOptions="gridOptions"
    >
    </ag-grid-vue>
  </div>
</template>

<script>
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";
import { AgGridVue } from "ag-grid-vue3";
export default {
  data() {
    return {
      gridOptions: null,
      gridApi: null,
      columnDefs: null,
      rowData: null,
      defaultColDef: null,
      gridColumnApi: null
    };
  },

  components: {
    "ag-grid-vue": AgGridVue,
  },

  methods: {
    sizeToFit() {
      this.gridApi.sizeColumnsToFit();
    },
    autoSizeAll(skipHeader) {
      var allColumnIds = [];
      this.gridColumnApi.getAllColumns().forEach(function (column) {
        allColumnIds.push(column.colId);
      });
      this.gridColumnApi.autoSizeColumns(allColumnIds, skipHeader);
    },
  },

  beforeMount() {
    this.gridOptions = {};
    this.defaultColDef = {
    //   width: 200,
      filter: "agTextColumnFilter",
      resizable: true,
    };
    this.columnDefs = [
      { headerName: "ID", field: "id", sortable: true},
      { headerName: "FOREIGN SOURCE", field: "foreignSource" },
      { headerName: "FOREIGN ID", field: "foreignId" },
      { headerName: "LABLE", field: "lable", sortable: true },
      { headerName: "LABLE SOURCE", field: "lableSource" },
      {
        headerName: "LAST CAPABILITIES SCAN",
        field: "lastCapabilitiesScan",
      },
      {
        headerName: "PRIMARY INTERFACE",
        field: "primaryInterface",
      },
      { headerName: "SYSOBJECTID", field: "sysObjectid" },
      { headerName: "SYSNAME", field: "sysName" },
      {
        headerName: "SYSDESCRIPTION",
        field: "sysDescription",
      },
      { headerName: "SYSCONTACT", field: "sysContact" },
      { headerName: "SYSLOCATION", field: "sysLocation" },
    ];

    this.rowData = [
      {
        id: "1",
        foreignSource: null,
        foreignId: null,
        lable: "192.168.1.127",
        lableSource: "A",
        lastCapabilitiesScan: null,
        primaryInterface: null,
        sysObjectid: null,
        sysName: null,
        sysDescription: null,
        sysContact: null,
        sysLocation: null,
      },
      {
        id: "2",
        foreignSource: null,
        foreignId: null,
        lable: "jie-mb",
        lableSource: "H",
        lastCapabilitiesScan: null,
        primaryInterface: null,
        sysObjectid: null,
        sysName: null,
        sysDescription: null,
        sysContact: null,
        sysLocation: null,
      },
    ];
  },

  mounted() {
    this.gridApi = this.gridOptions.api;
    this.gridColumnApi = this.gridOptions.columnApi;
    this.sizeToFit();
    // this.autoSizeAll(false);
  },
};
</script>

<style scoped></style>
