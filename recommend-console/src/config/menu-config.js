const base = 'home'

export default [{
  name: 'basic',
  meta: {
    title: '推荐列表',
    icon: 'fa fa-camera-retro'
  },
  sub: [{
    name: 'HotProduct',
    meta: {
      index: `/home/HotProduct`,
      title: '最热门商品排行',
      type: 'menu',
      active: false
    }
  }, {
    name: 'MostProduct',
    meta: {
      index: `/home/MostProduct`,
      title: '评分最多商品排行',
      type: 'menu',
      active: false
    }
  },{
    name: 'OfflineProduct',
    meta: {
      index: `/home/OfflineProduct`,
      title: '离线推荐',
      type: 'menu',
      active: false
    }
  },{
      name: 'OnlineProduct',
      meta: {
        index: `/home/OnlineProduct`,
        title: '实时推荐',
        type: 'menu',
        active: false
      }
    }]
}]
