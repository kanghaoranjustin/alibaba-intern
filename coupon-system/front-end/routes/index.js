var express = require('express');
var router = express.Router();
const request = require('request');
const fetch = require('node-fetch');

/* GET home page. */
router.get('/manageGood', function(req, res, next) {
  var goodArray;
  var typeArray
  request("http://localhost:8080/goods/offline", function (error, response, body) {
    if (!error && response.statusCode == 200) {
      goodArray = JSON.parse(body);
    }
  });

  request("http://localhost:8080/goods/types", function (error, response, body) {
    if (!error && response.statusCode == 200) {
      typeArray = JSON.parse(body);
    }
  });

  setTimeout(() => {
    var names = [];
    for(var i = 0; i<goodArray.length; i++){
        names.push(goodArray[i].name);
    }

    res.render('index', { title: '商品平台', nameArray: names, typeArray: typeArray });
  }, 500);
});

router.get('/get/:type', function(req, res, next){
  var type = req.params.type;
  var goodArray;
  request("http://localhost:8080/goods/byType/" + type, function (error, response, body) {
  if (!error && response.statusCode == 200) {
     goodArray = JSON.parse(body);
  }
});
setTimeout(() => { 
  var names = [];
  for(var i = 0; i<goodArray.length; i++){
      names.push(goodArray[i].name);
  }
  res.render('type_display', { type_name: type, nameArray: names});
}, 100);
  
});

router.post('/submit', function(req, res, next) {
  var type = req.body.get_type;
  res.redirect("/get/" + type);
});

router.post('/editGood', function(req, res, next) {
  var good = req.body.edit;
  res.redirect("/editGood/" + good);
});

router.get('/editGood/:good', function(req, res, next){
  var good = req.params.good;
  var detail;
  request("http://localhost:8080/goods/byName/" + good, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      detail = JSON.parse(body);
    }
  });
  setTimeout(() => { 
  res.render("editGood", { goodName: good, type: detail.type, number: detail.number, status: detail.status, price: detail.price });
}, 100);
});

router.post('/editGood/:good', function(req, res, next) {
  var good = req.params.good;
  if(req.body.change == "inventory"){
    request.put("http://localhost:8080/goods/update_inventory/" + req.body.inventory +"/"+ good, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        console.log(body);
      }
    });

    setTimeout(() => { res.redirect("/editGood/"+good) }, 100);

  }
  else{
    request.put("http://localhost:8080/goods/update_status/off/" + good, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        console.log(body);
      }
    });

    setTimeout(() => { res.redirect("/editGood/"+good) }, 100);
  }
});

router.post('/offline', function(req, res, next) {
  var good = req.body.turnOn;
  request.put("http://localhost:8080/goods/update_status/on/" + good, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        console.log(body);
      }
    });
    setTimeout(() => {res.redirect("/manageGood") }, 100);
});

router.post('/createG', function(req, res, next) {
  res.render("createGood");
});

router.post('/createGood', function(req, res, next) {
  var url = "http://localhost:8080/goods/?name=" +req.body.name+ "&type=" + req.body.type + "&number="+req.body.inventory+"&price="+req.body.price+"&status=上线";
  request.post(encodeURI(url), function (error, response, body) {
      if (!error && response.statusCode == 200) {
        console.log(body);
      }
      else{
        res.send(error);
      }
    });
    setTimeout(() => {res.redirect("/manageGood") }, 100);
});


router.get('/', function(req, res, next) {
  res.render("login");
});

router.get('/user_info/:username', async function(req, res, next) {
  res.render("user_info", {username: req.params.username});
});

router.post('/login', async function(req, res, next) {
  if(req.body.log == "login"){
    try{
      const response = await fetch("http://localhost:8080/users/" + req.body.username);
      const json = await response.json();
      real = json.password;

      if(real == req.body.password){
          res.redirect("/user_info/" + req.body.username);
      }
      else{
          res.redirect("/");
      }
      }
    catch(err){
        res.redirect("/");
    }
  }

  else if(req.body.log == "register")
    res.redirect("/register");
  
  else if(req.body.log == "manageGood")
    res.redirect("/manageGood");

  else
    res.redirect("/createCoupon");
  
  
});

router.get('/register', async function(req, res, next) {
  res.render("register");
});

router.post('/register', async function(req, res, next) {
  var result;
  request.post("http://localhost:8080/users/?username="+req.body.username+"&password="+req.body.password,
                    (err, res, body) => {
                            result = body;
                        }
                    );

                    setTimeout(() => {
                        if(result == "success")
                            res.redirect("/");
                        else
                            res.redirect("/register");
                     }, 100);              
});

router.get('/createCoupon', async function(req, res, next) {
  res.render("createCoupon");
});

router.post('/createCoupon', async function(req, res, next) {
  var detail;
                if(req.body.type == "mj"){
                            detail = "满"+req.body.man+","+"减"+body.jian;}
                        else if(req.body.type == "zkj"){
                            detail = "折扣"+req.body.zkj+"%";}
                        else{
                            detail = "立减"+req.body.lj;
                        }
                var u = "http://localhost:8080/coupons/?name="+req.body.coupon_name+"&num="+req.body.num+"&type="+req.body.type+"&instruction="+req.body.instruction+"&detail="+detail+"&start_date="+req.body.start_date+"&end_date="+req.body.end_date;
                console.log(u);
                request.post(encodeURI(u),
                                    (err, res, body) => {
                                    if(err){console.error(err);}
                                            else{console.log(body);}
                                        }
                                    );
                                    res.redirect("/createCoupon")
});

router.post('/userAct/:username', function(req, res, next) {
  var user = req.params.username;
  if(req.body.act == "wallet")
    res.redirect("/wallet/" + user);
  else if(req.body.act == "coupon")
    res.redirect("/displayCoupon/" + user);
  else if(req.body.act == "good")
    res.redirect("/userGood/" + user);
  else if(req.body.act == "cart")
    res.redirect("/cart/" + user);
  else{
    request.put("http://localhost:8080/users/balance/"+req.body.topup+"/"+user,
        (err, res, body) =>{ 
           console.log(body);
        });
    setTimeout(() => { res.redirect("/user_info/" + user)}, 100);
  }
});

router.get('/displayCoupon/:username', async function(req, res, next) {
  var user = req.params.username
  const urls = "http://localhost:8080/coupons/";
  const response = await fetch(urls);
  const coupons = await response.json();
  var couponList = [];

  for(var i=0; i<coupons.length; i++){
      couponList.push(coupons[i].name + ": " + coupons[i].detail);
  }

  res.render("displayCoupon", {username: user, couponArray: couponList});
});

router.post('/displayCoupon/:username', async function(req, res, next) {
  request.put("http://localhost:8080/coupons/get/"+req.params.username+"/"+req.body.coupon.split(":")[0],
                                    (err, res, body) =>{ 
                                            console.log(body);
               });
  
  setTimeout(() => {res.redirect("/wallet/" + req.params.username) }, 100);
});


router.get('/wallet/:username', async function(req, res, next) {
  const response = await fetch("http://localhost:8080/users/"+req.params.username);
            const user = await response.json();
            try{
              var coupons = user.coupon.split(",");
            }
            catch(err){
              var coupons = [];
            }
            var couponList = [];
            for(var i = 0; i<coupons.length; i++){
                         const name = coupons[i];
                         const resp = await fetch("http://localhost:8080/coupons/"+name);
                         const coupon = await resp.json();
                         couponList.push(coupon.name + ": " + coupon.detail);
                     }
            res.render("walletCoupon", { couponArray: couponList });
});

router.get("/userGood/:username", function(req, res, next){
  var typeArray;
  var user = req.params.username;
  request("http://localhost:8080/goods/types", function (error, response, body) {
    if (!error && response.statusCode == 200) {
      typeArray = JSON.parse(body);
    }
  });

  setTimeout(() => { res.render("typeSale", {typeArray: typeArray, username: user}) }, 100);
});

router.post("/userGood/:username", function(req, res, next){
  var user = req.params.username;
  var type = req.body.get_type;
  res.redirect("/goodForSale/" + user + "/" + type);
});

router.get("/goodForSale/:username/:type", function(req, res, next){
  var user = req.params.username;
  var type = req.params.type;
  var goodArray;
  request("http://localhost:8080/goods/byType/" + type, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      goodArray = JSON.parse(body);
    }
  });
  setTimeout(() => { 
    var sales = [];
    for(var i = 0; i<goodArray.length; i++){
        sales.push(goodArray[i].name + ": " + goodArray[i].price + "元");
    }
    res.render('goodSaleByType', { username: user, type_name: type, saleArray: sales});
  }, 100);
});

router.post("/toCart/:username", function(req, res, next){
  var user = req.params.username;
  var good = req.body.good;
  request.put("http://localhost:8080/goods/toCart/" + user + "/" + good.split(":")[0], function (error, response, body) {
    if (!error && response.statusCode == 200) {
      console.log(body);
    }
  });

  setTimeout(() => { res.redirect("/cart/" + user) }, 100);
});

router.get("/cart/:username", async function(req, res, next){
  var username = req.params.username;

  const response = await fetch("http://localhost:8080/users/"+username);
            const user = await response.json();
            try{
              var goods = user.good.split(",");
            }
            catch(err){
              var goods = [];
            }
            var goodList = [];
            for(var i = 0; i<goods.length; i++){
                         const name = goods[i];
                         goodList.push(name);
                     }
  res.render("cart", {goodArray: goodList, username: username})
});

router.post("/cart/:username", async function(req, res, next){
  var username = req.params.username;
  var good_purchase = req.body.buy;
  const resp = await fetch("http://localhost:8080/goods/byName/"+good_purchase);
  const good_detail = await resp.json();
  var price = good_detail.price;
  var new_inventory = Number(good_detail.number) - 1;
  request.put("http://localhost:8080/goods/update_inventory/" + String(new_inventory) +"/"+ good_purchase, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        console.log(body);
      }
    });

  request.put("http://localhost:8080/users/purchase/" + good_purchase +"/"+ username, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        console.log(body);
      }
    });

    setTimeout(() => { 
      res.render("purchase", {username: username, price: price, good: good_purchase})
    }, 100);
});



module.exports = router;
