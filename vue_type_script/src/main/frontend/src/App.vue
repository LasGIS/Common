<template>
  <div id="nav">
    <router-link to="/">Layout</router-link>
    <router-link to="/hello">Hello</router-link>
    <router-link to="/call-service">Service</router-link>
    <router-link to="/bootstrap">Bootstrap</router-link>
    <router-link to="/user">User</router-link>
    <router-link to="/login">Login</router-link>
    <router-link to="/protected">Protected</router-link>
  </div>
  <router-view />
</template>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    margin-left: 10px;
    font-weight: bold;
    color: #2c3e50;

    &:first-child {
      margin-left: 0;
    }

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
