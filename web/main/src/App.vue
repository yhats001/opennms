<template>
  <router-view/>
</template>

<style lang="scss">
  $primary-blue: rgba($color: #0081AD, $alpha: 1);
  a {
    text-decoration: none;
    color: $primary-blue;
  }
  .flex-container {
    padding: 0;
    margin: 0;
    list-style: none;
    display: flex;
  }
  .space-between { 
    justify-content: space-between; 
  }
  .space-evenly {
    justify-content: space-evenly;
  }
  .top-10 {
    margin-top: 10px;
  }
  .page {
    max-width: 2400px;
    margin: auto
  }
  .p-datatable-header,
  .p-panel-header {
    background: $primary-blue !important;
    color: white !important;
  }
</style>
