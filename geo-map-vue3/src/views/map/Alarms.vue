<template>
  <div class="alarms">
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
      gridColumnApi: null,
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
      filter: "agTextColumnFilter",
      resizable: true,
      enableBrowserTooltips: true,
    };
    this.columnDefs = [
      { headerName: "ID", field: "id", sortable: true, headerTooltip: "ID" },
      {
        headerName: "SEVERITY",
        field: "severity",
        sortable: true,
        headerTooltip: "Severity",
      },
      {
        headerName: "Node",
        field: "node",
        sortable: true,
        headerTooltip: "Node",
      },
      {
        headerName: "UEI",
        field: "lable",
        sortable: true,
        headerTooltip: "Lable",
      },
      {
        headerName: "LABLE SOURCE",
        field: "uei",
        sortable: true,
        headerTooltip: "UEI",
      },
      {
        headerName: "COUNT",
        field: "count",
        sortable: true,
        headerTooltip: "Count",
      },
      {
        headerName: "LAST EVENT TIME",
        field: "lastEventTime",
        sortable: true,
        headerTooltip: "Last Event Time",
      },
      {
        headerName: "LOG MESSAGE",
        field: "logMessage",
        sortable: true,
        headerTooltip: "Log Message",
      },
    ];

    this.rowData = [
      {
        id: "64",
        severity: "Major",
        node: "jies-mbp",
        uei: "uei.opennms.org/nodes/nodeDown",
        count: "1",
        lastEventTime: "2021-07-23T09:59:41-04:00",
        logMessage: "Node jies-mbp is down.",
      },
      {
        id: "55",
        severity: "Major",
        node: "192.168.1.127",
        uei: "uei.opennms.org/provisioner/provisioningAdapterFailed",
        count: "3",
        lastEventTime: "2021-07-22T09:00:36-04:00",
        logMessage: "<p>A provisioning adapter failed for host.</p>",
      },
      {
        id: "54",
        severity: "Major",
        node: "jies-mbp",
        uei: "uei.opennms.org/provisioner/provisioningAdapterFailed",
        count: "11",
        lastEventTime: "2021-07-22T09:00:38-04:00",
        logMessage: "<p>A provisioning adapter failed for host.</p>",
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
