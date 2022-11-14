package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class PostRepository {
  public List<Post> list = new ArrayList<>();
  Optional<Post> opt;
  int count = 0;

  public List<Post> all() {
    return list;
  }

  public Optional<Post> getById(long id) {
    synchronized (this) {
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getId() == id) {
          opt = Optional.ofNullable(list.get(i));
          if (opt.isEmpty()) {
            throw new NotFoundException("Not Found Exception");
          }
        }
      }
      return opt;
    }
  }

  public Post save(Post post) {
    synchronized (this) {
      if (post.getId() == 0) {
        post.setId(count);
        list.add(post);
        count++;
      }
      if (post.getId() != 0) {
        for (int i = 0; i < list.size(); i++) {
          if (post.getId() == i) {
            list.set(i, post);
          }
        }
        if (post.getId() > list.size()) {
          throw new NotFoundException("Not Found Exception");
        }
      }
      return post;
    }
  }

  public void removeById(long id) {
    synchronized (this) {
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getId() == id) {
          list.remove(i);
        }
      }
    }
  }
}
