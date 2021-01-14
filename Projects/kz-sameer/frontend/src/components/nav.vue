<template>
  <div>
    <b-container>
      <b-row>
        <b-col>
          <b-button :disabled='first' @click="fPage">&laquo;</b-button>
        </b-col>
        <b-col>
          <b-button :disabled='first' @click="prevPage">&lsaquo;</b-button>
        </b-col>
        <b-col lg='3'>
            <b-button-group>
                <b-button>{{ page }}</b-button>
                <b-dropdown right text="Page Size">
                    <b-dropdown-item @click="pageSize(5)">5</b-dropdown-item>
                    <b-dropdown-item @click="pageSize(10)">10</b-dropdown-item>
                    <b-dropdown-item @click="pageSize(15)">15</b-dropdown-item>
                    <b-dropdown-item @click="pageSize(20)">20</b-dropdown-item>
                    <b-dropdown-item @click="pageSize(25)">25</b-dropdown-item>
                    <b-dropdown-item @click="pageSize(30)">30</b-dropdown-item>
                </b-dropdown>
            </b-button-group>
        </b-col>
        <b-col>
          <b-button :disabled='last' @click="nextPage">&rsaquo;</b-button>
        </b-col>
        <b-col>
          <b-button :disabled='last' @click="lPage">&raquo;</b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>

</template>

<script>

export default
{
    props: [
        'page',
        'pageSize',
        'nextPage',
        'prevPage',
        'last',
        'first',
        'lPage',
        'fPage'
    ]
};
</script>

<style>
</style>