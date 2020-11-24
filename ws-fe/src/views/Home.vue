<template>
  <div>
    <web-socket-panel :connection="connectionOne" :topic="topicOne">
      <template v-slot:default="slotProps">
        Name: {{ slotProps.data.name }} <br />
        Surname: {{ slotProps.data.surname }} <br />
        Age: {{ slotProps.data.age }}
      </template>
    </web-socket-panel>
    <web-socket-panel :connection="connectionTwo" :topic="topicTwo">
      <template v-slot:default="slotProps">
        Name: {{ slotProps.data.name }} <br />
        Breed: {{ slotProps.data.breed }}
      </template>
    </web-socket-panel>
  </div>
</template>

<script>
import WebSocketPanel from "@/components/WebSocketPanel";

export default {
  name: "Home",
  components: { WebSocketPanel },
  data: function() {
    return {
      connectionOne: "/people-app/ws",
      topicOne: "/people/random",
      connectionTwo: "/pets-app/ws",
      topicTwo: "/pets/random"
    };
  }
};
</script>
