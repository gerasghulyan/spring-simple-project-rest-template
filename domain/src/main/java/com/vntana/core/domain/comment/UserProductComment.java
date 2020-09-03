package com.vntana.core.domain.comment;

import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Geras Ghulyan
 * Date: 9/3/20
 * Time: 4:35 PM
 */
@Entity
@Table(
        name = "user_comment_product"
)
@DiscriminatorValue("COMMENT_PRODUCT")
public class UserProductComment extends AbstractUserComment {

    @Column(name = "product_uuid", nullable = false, updatable = false)
    private String productUuid;

    public UserProductComment() {
    }

    public UserProductComment(final User user, final String productUuid) {
        super(user);
        this.productUuid = productUuid;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProductComment)) {
            return false;
        }
        final UserProductComment that = (UserProductComment) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(productUuid, that.productUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(productUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("productUuid", productUuid)
                .toString();
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(final String productUuid) {
        this.productUuid = productUuid;
    }
}
