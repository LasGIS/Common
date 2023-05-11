<template>
  <div class="header-container">
    <div class="header">
      <router-link class="header__logo" to="/" />
      <div class="header__menu">
        <router-link to="/help/content" class="header__btn-all-services"> Помощь </router-link>
        <!-- <Balloon elements="{USER_MENU_ELEMENTS}" onSelect="{this.onUserMenuSelect}">-->
        <div class="header__btn header__btn-user-menu">
          {{ user }}
          <div class="header__btn-user-arrow" />
        </div>
        <!-- </Balloon>-->
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  name: 'MyHeader',
  props: {
    user: {
      type: String,
      require: true,
    },
  },
};
</script>

<style scoped></style>
