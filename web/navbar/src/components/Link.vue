<template>
  <h3 class="link">
    <router-link :to="to">
      <slot></slot>
    </router-link>
  </h3>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  props: {
    to: {
      required: true,
      type: String
    }
  }
})
</script>

<style lang="scss" scoped>
  $linkColor: #14D1DF;

  .link {
    width: 100%;

    a {
      text-decoration: none;
      color: $linkColor;
      margin-left: 50px;
      font-family: "Segoe UI", Roboto, Helvetica;
    }
  }
</style>
