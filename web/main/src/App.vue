<template>
  <router-view/>
</template>

<style lang="scss">
  $primary-blue: rgba($color: #0081AD, $alpha: 1);
  $primary-green: rgba($color: #85D9A5, $alpha: 1);

  h1, h2, h3, h4, h5, h6 {
    font-family: "Segoe UI", Roboto, Helvetica;
  }
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

  // header theme
  .p-datatable-header,
  .p-panel-header {
    background: $primary-blue !important;
    color: white !important;
  }


  // Button theme
  .p-button {
    background: $primary-blue !important;
    &:hover {
      background: rgba($color: $primary-blue, $alpha: 0.8) !important;
    }
  }
  .p-button-success {
    background: $primary-green !important;
    border: none !important;
    &:hover {
      background: rgba($color: $primary-green, $alpha: 0.8) !important;
    }
  }
</style>
