namespace java com.gopersist.demo.tsh

struct Blog {
  1: required i64 id,
  2: required string blogName,
  3: required string content,
  4: optional string author
}

exception RequestException {
  1: required i32 errorCode,
  2: required string errorMessage,
  3: optional map<string, string> errorFields
}

service Blogs {
  list<Blog> listAll(),
  Blog create(1: Blog blog) throws (1: RequestException re),
  Blog update(1: Blog blog) throws (1: RequestException re),
  void deleteById(1: i64 id) throws (1: RequestException re)
}