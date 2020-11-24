<template>
  <div class="ws-panel-container">
    <div v-if="connected">
      Connected to {{ connection }}
      <p v-if="streamed">
        <slot v-bind:data="streamed">
          {{ streamed }}
        </slot>
      </p>
      <p v-else>No data retrieved yet.</p>
    </div>
    <div v-else style="color:red">Not connected to {{ connection }}</div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";

export default {
  name: "WebSocketPanel",
  props: ["connection", "topic"],
  data: function() {
    return {
      streamed: null,
      stompClient: null,
      connected: false,
      reconnectDelay: 10000 //10 seconds.
    };
  },
  mounted() {
    this.connect();
  },
  methods: {
    connect() {
      const socket = new SockJS(this.connection);
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect(
        {},
        () => {
          this.connected = true;
          this.stompClient.subscribe(this.topic, data => {
            this.streamed = JSON.parse(data.body);
          });
        },
        error => {
          console.error(error);
          this.connected = false;

          console.info(
            "Retrying connection in " +
              this.reconnectDelay / 1000 +
              " seconds..."
          );
          this.reconnect = setTimeout(() => {
            this.connect();
          }, this.reconnectDelay);
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
        console.info("Stomp Client disconnected.");
      }
      this.connected = false;
    }
  },
  beforeDestroy() {
    console.info("Destroying connections....");
    this.disconnect();
    clearTimeout(this.reconnect);
  }
};
</script>

<style>
.ws-panel-container {
  border: 1px solid;
  text-align: center;
  margin: 2% auto;
  padding: 2%;
}
</style>
