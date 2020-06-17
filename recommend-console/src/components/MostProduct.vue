<template>
  <el-table
    :data="productList"
    style="width: 100%"
    height="900">
    <el-table-column
      fixed
      prop="productId"
      label="商品id"
      width="100">
    </el-table-column>
    <el-table-column
      prop="name"
      label="商品名称"
      width="120">
    </el-table-column>
    <el-table-column
      prop="categories"
      label="种类"
      width="200">
    </el-table-column>
    <el-table-column
      prop="tags"
      label="标签"
      width="300">
    </el-table-column>
    <el-table-column
      prop="imgUrl"
      header-align="center"
      align="center"
      width="150px"
      label="图片">
      <template slot-scope="scope">
        <el-popover
            placement="right"
            title=""
            trigger="hover">
            <img :src="scope.row.imgUrl"/>
            <img slot="reference" :src="scope.row.imgUrl" :alt="scope.row.imgUrl" style="max-height: 50px;max-width: 130px">
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column
      prop=""
      label="评分"
      align="center">
        <template slot-scope="scope">   
          <el-rate
            v-model="score"
            :colors="colors"
            :allow-half="true"
            @change="(score) => rate(score, scope.row)">
          </el-rate>
        </template>
    </el-table-column>
  </el-table>
</template>



<script>
import axios from 'axios'
  export default {
    data() {
      return {
        productList:[],
        colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
        score: null,
      }
    },
    created() {
      this.initProduct()
    },
    methods: {
      initProduct() {
        this.userId = this.$route.query.userId
        // 热门商品
        var hotProduct = null
        axios.get("http://localhost:9001/api/product/most", {
           params: {
             num: 20
           }
        }).then(res => {
          if (res.data.code == 0) {
            //console.log(res.data.data)
            this.productList = res.data.data
          }
        }).catch(err => {
          console.log(err)
        })
      },
      rate(score, scope) {
        // 打分    
        console.log(scope)
        axios.post("http://localhost:9001/api/product/rate", {
          productId: scope.productId,
          userId: this.userId, 
          score: score
        }).then(res => {
          if (res.data.code == 0) {
            console.log("打分成功")
            this.$notify({
              title: '打分成功',
              type: 'success',
              duration: 3000
            })
          } else {
            this.$notify({
              title: 'Sorry',
              type: 'success',
              message: res.data.message,
              duration: 3000
            })
          }
        }).catch(err => {
          console.log(err)
        })
      }
    }
  }
</script>

