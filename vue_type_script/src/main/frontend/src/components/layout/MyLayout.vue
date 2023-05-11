<template>
  <div class="page">
    <div class="page__header" key="page__header">
      <MyHeader :user="user" />
    </div>
    <div class="page__menu" key="page_menu">
      <!--<Menu
        actions="{menuActions}"
        location="{location}"
        authorities="{authorities}"
        haveRole="{haveRole}"
        internationalMonitoring="{internationalMonitoring}"
      />-->
    </div>
    <div class="page__content"><router-view /></div>
    <div class="page__footer">
      <!--<Footer supportUrl="{supportUrl}" />-->
    </div>
    <!--
      <NotificationForm done={this.onCloseNotification} message={notificationMessage} show={showNotification} style={style} />
      <Spinner show={showSpinner} />
      <ConfirmForm
        done={done}
        message={confirmMessage}
        show={showConfirm}
        close={this.closeConfirm}
        submitButtonLabel={submitButtonLabel}
        cancelButtonLabel={cancelButtonLabel}
      />
    -->
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import MyHeader from '@/components/layout/MyHeader.vue';

interface State {
  user: string | undefined;
}

export default defineComponent({
  name: 'MyLayout',

  data: (): State => {
    return {
      user: 'Header User',
    };
  },
  components: { MyHeader },
  //  computed:
});
</script>

<style scoped></style>
