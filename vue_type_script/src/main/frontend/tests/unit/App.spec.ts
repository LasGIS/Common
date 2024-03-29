import { shallowMount } from '@vue/test-utils';
import App from '@/App.vue';

// see https://vue-test-utils.vuejs.org/guides/using-with-vue-router.html#testing-components-that-use-router-link-or-router-view
describe('App component should', () => {
  it('render without crashing', () => {
    const wrapper = shallowMount(App);
    expect(wrapper.html()).toContain('<div id="nav">');
  });
});
