function handleSubmit(content, workspace, identifier, text) {
  $.ajax({
    url: '/modules/api/jcr/v1/' + workspace + '/en/nodes/' + identifier + '/children/comment-' + Math.floor((Math.random() * 10000) + 1),
    type: 'put',
    contentType: "application/json",
    dataType: 'json',
    data: JSON.stringify({
      "type": "javaone:comment",
      "properties": {
        "text": {
          "value": text
        }
      }
    }),
    success: function (data) {
      content.append('<div class="comment"><span>' + data.properties.text.value + '</span></div>');
    }
  });
}