export default {
    setAccount: ({ commit }, payload) => {
        commit('setAccount', payload);
    },

    setBuyer: ({ commit }, payload) => {
        commit('setBuyer', payload);
    },

    setItems: ({ commit }, payload) => {
        commit('setItems', payload);
    }
}