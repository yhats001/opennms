<template>
  <div id="nav" >
    <img id="logo" src="require('./assets/opennms-horizon-logo.svg')" alt=" OpenNMS Horizon Logo" width="auto" height="22px" />
    <router-link to="/" >Home</router-link> |
    <router-link to="/map">Map</router-link>
  </div>
  <router-view />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 1.25%;
  background: #0a0c1b;
}

#nav a {
  font-family: Arial;
  font-size: 15px;
  font-weight: bold;
  color: #B4B6C8;
}

#nav a.router-link-exact-active {
  color: #E9EBF9;
}
</style>
