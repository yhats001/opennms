<template>
  <router-view/>
</template>

<style lang="scss">
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
  .top-30 {
    margin-top: 30px;
  }
  .page {
    max-width: 2400px;
    margin: auto
  }
</style>
