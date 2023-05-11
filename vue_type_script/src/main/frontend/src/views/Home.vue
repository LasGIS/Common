<template>
  <div class="home">
    <HelloSpringWorld hellomsg="Welcome to your Vue.js (+ TypeScript) powered Spring Boot App" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import HelloSpringWorld from '@/components/HelloSpringWorld.vue'; // @ is an alias to /src

export default defineComponent({
  name: 'Home',
  components: {
    HelloSpringWorld,
  },
});
</script>
